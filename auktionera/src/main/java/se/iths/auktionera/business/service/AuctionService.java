package se.iths.auktionera.business.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import se.iths.auktionera.api.exception.InvalidBidException;
import se.iths.auktionera.api.exception.InvalidCategoryException;
import se.iths.auktionera.api.exception.NotFoundException;
import se.iths.auktionera.business.model.*;
import se.iths.auktionera.persistence.entity.*;
import se.iths.auktionera.persistence.repo.*;

import java.time.Instant;
import java.util.*;

@Service
public class AuctionService implements IAuctionService {

    private final AuctionRepo auctionRepo;
    private final AccountRepo accountRepo;
    private final UserStatsRepo userStatsRepo;
    private final CategoryRepo categoryRepo;
    private final TagsRepo tagsRepo;

    public AuctionService(AuctionRepo auctionRepo, AccountRepo accountRepo, UserStatsRepo userStatsRepo, CategoryRepo categoryRepo, TagsRepo tagsRepo) {
        this.auctionRepo = auctionRepo;
        this.accountRepo = accountRepo;
        this.userStatsRepo = userStatsRepo;
        this.categoryRepo = categoryRepo;
        this.tagsRepo = tagsRepo;
    }

    @EventListener
    public void onStart(ApplicationEvent event){
        List<AuctionEntity> expiredAuctions = auctionRepo.findByAuctionStateAndEndsAtIsLessThanEqual(AuctionState.INPROGRESS, Instant.now());
        if (expiredAuctions.size() > 0 ) {
            for (AuctionEntity auction: expiredAuctions)
                { if (auction.getCurrentBid() > 0 && auction.getCurrentBidAt().isBefore(auction.getEndsAt())) {
                    AccountEntity acc = Objects.requireNonNull(accountRepo.findById(auction.getLatestBidder())).orElseThrow();
                    Optional.of(acc).ifPresent(auction::setBuyer);
                    auction.setEndedAt(Instant.now());
                    auction.setAuctionState(AuctionState.ENDEDBOUGHT);
                    auctionRepo.saveAndFlush(auction);
                    updateUserStats(auction);
                } else {
                    auction.setAuctionState(AuctionState.ENDEDNOTBOUGHT);
                    auctionRepo.saveAndFlush(auction);
                }
            }
            System.out.println("Auction-list has been updated " + event.getTimestamp());
        }
    }

    @Override
    public List<Auction> getAuctions(Map<String, String> filters, Map<String, String> sorters) {
        List<AuctionEntity> auctionsFound = auctionRepo.findByAuctionStateAndEndsAtIsGreaterThan(AuctionState.INPROGRESS, Instant.now()); //findAllByAuctionState(AuctionState.INPROGRESS);
        List<Auction> auctionsToReturn = new ArrayList<>();
        for (AuctionEntity auctionEntity : auctionsFound) {
            auctionsToReturn.add(new Auction(auctionEntity));
        }
        return auctionsToReturn;
    }

    @Override
    public Auction getAuctionById(long id) {
        AuctionEntity auctionEntity = auctionRepo.findById(id).orElseThrow(() -> new NotFoundException("No auction with id: " +
                id + " was found. Please insert a valid auction id."));
        return new Auction(auctionEntity);
    }

    @Override
    public Auction createAuction(String userName, AuctionRequest auctionRequest) {
        AccountEntity seller = accountRepo.findByUserName(userName);
        AuctionEntity auctionToBeCreated = AuctionEntity.builder()
                .description(auctionRequest.getDescription())
                .seller(seller)
                .auctionState(AuctionState.INPROGRESS)
                .endsAt(auctionRequest.getEndsAt())
                .createdAt(Instant.now())
                .startPrice(auctionRequest.getStartPrice())
                .buyOutPrice(auctionRequest.getBuyoutPrice())
                .minBidStep(auctionRequest.getMinBidStep())
                .deliveryType(auctionRequest.getDeliveryType()).build();
        CategoryEntity currentCategory = categoryRepo.findByCategoryTitle(auctionRequest.getCategory()).orElseThrow(() ->
                new InvalidCategoryException(auctionRequest.getCategory() +
                        "is not a valid category. Please select between these categories: " + printAllCategories()));
        Set<TagsEntity> tags = convertListToSet(auctionRequest.getTags());
        tagsRepo.saveAll(tags);
        auctionToBeCreated.setTags(tags);
        auctionToBeCreated.setCategory(currentCategory);
        currentCategory.getAuctions().add(auctionToBeCreated);
        auctionRepo.saveAndFlush(auctionToBeCreated);
        categoryRepo.saveAndFlush(currentCategory);
        seller.getAuctionEntities().add(auctionToBeCreated);
        accountRepo.saveAndFlush(seller);
        return new Auction(auctionToBeCreated);
    }

    public Set<TagsEntity> convertListToSet(List<String> list) {
        Set<TagsEntity> setToReturn = new HashSet<>();
        for (String s: list) {
            TagsEntity t = new TagsEntity();
            t.setTag(s);
            setToReturn.add(t);
         }
        return setToReturn;
    }

    @Override
    public void deleteAuctionById(Long id, String userName) {
        AuctionEntity auctionEntity = auctionRepo.findById(id).orElseThrow(() -> new NotFoundException("No auction with id: " +
                id + " was found. Please insert a valid auction id."));
        AccountEntity acc = Objects.requireNonNull(accountRepo.findByUserName(userName));
        acc.getAuctionEntities().remove(auctionEntity);
        CategoryEntity categoryEntity = categoryRepo.findByCategoryTitle(auctionEntity.getCategory().getCategoryTitle()).orElseThrow();
        categoryEntity.getAuctions().remove(auctionEntity);
        categoryRepo.saveAndFlush(categoryEntity);
        accountRepo.saveAndFlush(acc);
        auctionRepo.deleteById(id);
        auctionRepo.flush();
    }

    @Override
    public Auction addBidToAuction(Bid bid, Long id, String userName) {

        AuctionEntity auctionEntity = auctionRepo.findByIdAndAuctionState(id, AuctionState.INPROGRESS).orElseThrow(() -> new NotFoundException("No auction with id: " +
                id + " was found or is no longer active. Please insert a valid auction id."));
        Auction auction = new Auction(auctionEntity);
        AccountEntity acc = Objects.requireNonNull(accountRepo.findByUserName(userName));
        if (bid.getBid() < auction.getStartPrice() ||
                bid.getBid() <= auction.getCurrentBid()) throw new InvalidBidException("Cannot bid lower than starting price: " +
                auction.getStartPrice() + " or lower than the current bid: " + auction.getCurrentBid());
        else {
            if (bid.getBid() == auction.getBuyOutPrice()) {
                Optional.of(bid.getBid()).ifPresent(auctionEntity::setCurrentBid);
                Optional.of(acc).ifPresent(auctionEntity::setBuyer);
                auctionEntity.setEndedAt(Instant.now());
                auctionEntity.setAuctionState(AuctionState.ENDEDBOUGHT);
                AuctionEntity updatedAuction = auctionRepo.saveAndFlush(auctionEntity);
                updateUserStats(updatedAuction);
                return new Auction(updatedAuction);
            }
            Optional.of(bid.getBid()).ifPresent(auctionEntity::setCurrentBid);
            Optional.of(acc.getId()).ifPresent(auctionEntity::setLatestBidder);
            auctionEntity.setCurrentBidAt(Instant.now());
            AuctionEntity updatedAuction = auctionRepo.saveAndFlush(auctionEntity);
            return new Auction(updatedAuction);
        }
    }

    private void updateUserStats(AuctionEntity updatedAuction) {
        UserStatsEntity sellerStats = userStatsRepo.findById(updatedAuction.getSeller().getId()).orElseThrow();
        sellerStats.setTotalSales(sellerStats.getTotalSales() + 1);
        UserStatsEntity buyerStats = userStatsRepo.findById(updatedAuction.getBuyer().getId()).orElseThrow();
        buyerStats.setTotalPurchases(buyerStats.getTotalPurchases() + 1);
        userStatsRepo.saveAndFlush(sellerStats);
        userStatsRepo.saveAndFlush(buyerStats);

    }

    @Override
    public List<Auction> getAuctionsForOneAccount(Map<String, String> filters, Map<String, String> sorters, String userName) {
        AccountEntity accountEntity = accountRepo.findByUserName(userName);
        List<AuctionEntity> auctionsFound = accountEntity.getAuctionEntities();
        List<Auction> auctionsToReturn = new ArrayList<>();
        for (AuctionEntity auctionEntity : auctionsFound) {
            auctionsToReturn.add(new Auction(auctionEntity));
        }
        return auctionsToReturn;
    }

    public String printAllCategories() {
        StringBuilder result = new StringBuilder();
        categoryRepo.findAll().forEach(c -> result.append(c.getCategoryTitle()).append(", "));
        return result.deleteCharAt(result.lastIndexOf(",")).toString();
    }

    private Sort orderBy(Map<String, String> sorters) {
        return null;
    }

}
