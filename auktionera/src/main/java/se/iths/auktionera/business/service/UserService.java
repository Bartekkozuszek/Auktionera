package se.iths.auktionera.business.service;

import org.springframework.stereotype.Service;
import se.iths.auktionera.api.exception.NotFoundException;
import se.iths.auktionera.business.model.Auction;
import se.iths.auktionera.business.model.User;
import se.iths.auktionera.business.model.UserStats;
import se.iths.auktionera.persistence.entity.AccountEntity;
import se.iths.auktionera.persistence.entity.AuctionEntity;
import se.iths.auktionera.persistence.repo.AccountRepo;
import se.iths.auktionera.persistence.repo.AuctionRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    private final AccountRepo accountRepo;
    private final AuctionRepo auctionRepo;

    public UserService(AccountRepo accountRepo, AuctionRepo auctionRepo) {
        this.accountRepo = accountRepo;
        this.auctionRepo = auctionRepo;
    }

    @Override
    public List<User> getUsers(Map<String, String> filters, Map<String, String> sorters) {
        List<AccountEntity> accountFound = accountRepo.findAll();
        List<User> users = new ArrayList<>();
        for (AccountEntity accountEntity : accountFound) {
            users.add(User.builder().
                    id(accountEntity.getId())
                    .userName(accountEntity.getUserName())
                    .createdAt(accountEntity.getCreatedAt())
                    .stats(UserStats.builder()
                            .totalPurchases(accountEntity.getUserStats().getTotalPurchases())
                            .totalSales(accountEntity.getUserStats().getTotalSales())
                            .sellerRating(accountEntity.getUserStats().getSellerRating())
                            .buyerRating(accountEntity.getUserStats().getBuyerRating()).build())
                    .build());
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {
        AccountEntity accountEntity = accountRepo.findById(id).orElseThrow(() -> new NotFoundException("User with id: "
                + id + " could not be found."));;

        return User.builder()
                .id(accountEntity.getId())
                .userName(accountEntity.getUserName())
                .createdAt(accountEntity.getCreatedAt())
                .stats(UserStats.builder()
                        .totalPurchases(accountEntity.getUserStats().getTotalPurchases())
                        .totalSales(accountEntity.getUserStats().getTotalSales())
                        .sellerRating(accountEntity.getUserStats().getSellerRating())
                        .buyerRating(accountEntity.getUserStats().getBuyerRating())
                        .build())
                .build();
    }

    @Override
    public List<Auction> getAuctionsByUser(Map<String, String> filters, Map<String, String> sorters, Long id) {
        AccountEntity entity = accountRepo.findById(id).orElseThrow(() -> new NotFoundException("No account with id: "
                + id + " was found. Please insert a valid user id."));
        List<AuctionEntity> auctionEntityList = auctionRepo.findAuctionsBySeller(entity);
        return auctionEntityList.stream().map(Auction::new).collect(Collectors.toList());
    }

}
