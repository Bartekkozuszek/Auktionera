package se.iths.auktionera.business.model;

import lombok.*;
import se.iths.auktionera.persistence.entity.UserStatsEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStats {
    private int totalSales;
    private int totalPurchases;
    private double sellerRating;
    private double buyerRating;
    private String reviewComments;

}
