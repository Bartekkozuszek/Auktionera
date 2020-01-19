package se.iths.auktionera.business.model;

import lombok.*;
import se.iths.auktionera.persistence.entity.ReviewEntity;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    private Long reviewId;
    private Double rating;
    private String reviewText;
    private User seller;
    private User buyer;

    public Review(ReviewEntity reviewEntity) {
        this.reviewId = reviewEntity.getReviewId();
        this.rating = reviewEntity.getRating();
        this.reviewText = reviewEntity.getReviewText();
        this.seller = User.builder()
                .id(reviewEntity.getSeller().getId())
                .userName(reviewEntity.getSeller().getUserName())
                .build();

        if (reviewEntity.getBuyer() != null) {
            this.buyer = User.builder()
                    .id(reviewEntity.getBuyer().getId())
                    .userName(reviewEntity.getBuyer().getUserName())
                    .build();
        }
    }
}
