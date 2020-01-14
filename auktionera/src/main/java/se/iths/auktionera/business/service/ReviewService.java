package se.iths.auktionera.business.service;

import org.springframework.stereotype.Service;
import se.iths.auktionera.business.model.Review;
import se.iths.auktionera.business.model.ReviewRequest;
import se.iths.auktionera.persistence.entity.AccountEntity;
import se.iths.auktionera.persistence.entity.AuctionEntity;
import se.iths.auktionera.persistence.entity.ReviewEntity;
import se.iths.auktionera.persistence.entity.UserStatsEntity;
import se.iths.auktionera.persistence.repo.AccountRepo;
import se.iths.auktionera.persistence.repo.AuctionRepo;
import se.iths.auktionera.persistence.repo.ReviewRepo;
import se.iths.auktionera.persistence.repo.UserStatsRepo;

import java.time.Instant;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService {
    private final AuctionRepo auctionRepo;
    private final ReviewRepo reviewRepo;
    private final AccountRepo accountRepo;
    private final UserStatsRepo userStatsRepo;

    public ReviewService(AuctionRepo auctionRepo, ReviewRepo reviewRepo, AccountRepo accountRepo, UserStatsRepo userStatsRepo) {
        this.auctionRepo = auctionRepo;
        this.reviewRepo = reviewRepo;
        this.accountRepo = accountRepo;
        this.userStatsRepo = userStatsRepo;
    }

//    @Override
//    public Review createSellerReview(ReviewRequest incomingReview, Long id) {
//        Optional<AuctionEntity> entityOptional = auctionRepo.findById(id);
//        AuctionEntity auctionEntity = new AuctionEntity();
//        if (entityOptional.isPresent()) auctionEntity = entityOptional.get();
//        ReviewEntity reviewToSave = ReviewEntity.builder()
//                .seller(auctionEntity.getSeller())
//                .buyer(auctionEntity.getBuyer())
//                .reviewText(incomingReview.getReviewText())
//                .rating(incomingReview.getRating())
//                .createdAt(Instant.now())
//                .lastEditAt(Instant.now())
//                .build();
//
//        reviewRepo.saveAndFlush(reviewToSave);
//        auctionEntity.setSellerReview(reviewToSave);
//        auctionEntity.getSeller().getReviewEntities().add(reviewToSave);
//        auctionRepo.saveAndFlush(auctionEntity);
//        return new Review(reviewToSave);
//    }
//
//    @Override
//    public Review createBuyerReview(ReviewRequest incomingReview, Long id) {
//        Optional<AuctionEntity> entityOptional = auctionRepo.findById(id);
//        AuctionEntity auctionEntity = new AuctionEntity();
//        if (entityOptional.isPresent()) auctionEntity = entityOptional.get();
//        ReviewEntity reviewToSave = ReviewEntity.builder()
//                .seller(auctionEntity.getSeller())
//                .buyer(auctionEntity.getBuyer())
//                .reviewText(incomingReview.getReviewText())
//                .rating(incomingReview.getRating())
//                .createdAt(Instant.now())
//                .lastEditAt(Instant.now())
//                .build();
//
//        reviewRepo.saveAndFlush(reviewToSave);
//        auctionEntity.setBuyerReview(reviewToSave);
//        auctionEntity.getBuyer().getReviewEntities().add(reviewToSave);
//        auctionRepo.saveAndFlush(auctionEntity);
//        return new Review(reviewToSave);
//    }
//
//    @Override
//    public void checkAccountAgainstSellerId(ReviewRequest reviewRequest, Long id) {
//        if (reviewRequest.isBuyer()) createSellerReview(reviewRequest, id);
//        else createBuyerReview(reviewRequest, id);
//    }

    @Override
    public Review createReview(ReviewRequest reviewRequest, Long id, String authId) {
        AccountEntity acc = accountRepo.findByAuthId(authId);
        Optional<AuctionEntity> entityOptional = auctionRepo.findById(id);
        AuctionEntity auctionEntity = new AuctionEntity();
        if (entityOptional.isPresent()) auctionEntity = entityOptional.get();
        ReviewEntity reviewToSave = ReviewEntity.builder()
                .seller(auctionEntity.getSeller())
                .buyer(auctionEntity.getBuyer())
                .reviewText(reviewRequest.getReviewText())
                .rating(reviewRequest.getRating())
                .createdAt(Instant.now())
                .lastEditAt(Instant.now())
                .build();

        reviewRepo.saveAndFlush(reviewToSave);

        if (acc.getId() == auctionEntity.getBuyer().getId()) {
            UserStatsEntity sellerStatsEntity = userStatsRepo.findById(auctionEntity.getSeller().getId()).orElseThrow();
            sellerStatsEntity.getReviewEntities().add(reviewToSave);
            sellerStatsEntity.setSellerRating(sellerStatsEntity.getSellerRating());
            userStatsRepo.saveAndFlush(sellerStatsEntity);
        } else {
            UserStatsEntity buyerStatsEntity = userStatsRepo.findById(auctionEntity.getBuyer().getId()).orElseThrow();
            buyerStatsEntity.getReviewEntities().add(reviewToSave);
            buyerStatsEntity.setBuyerRating(buyerStatsEntity.getBuyerRating());
            userStatsRepo.saveAndFlush(buyerStatsEntity);
        }

        return new Review(reviewToSave);
    }
}
