package se.iths.auktionera.business.model;

import lombok.*;
import se.iths.auktionera.persistence.entity.AccountEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private User user;
    private String email;
    private boolean anonymousBuyer;
    private Address address;

    public Account(AccountEntity ent) {
        this.address = Address.builder()
                .streetName(ent.getStreetName())
                .postNr(ent.getPostNr())
                .city(ent.getCity())
                .build();

        this.user = User.builder()
                .id(ent.getId())
                .userName(ent.getUserName())
                .createdAt(ent.getCreatedAt())
                .stats(UserStats.builder()
                        .totalPurchases(ent.getUserStats().getTotalPurchases())
                        .totalSales(ent.getUserStats().getTotalSales())
                        .buyerRating(ent.getUserStats().getBuyerRating())
                        .sellerRating(ent.getUserStats().getSellerRating()).build())
                .build();

        this.email = ent.getEmail();
        this.anonymousBuyer = ent.isAnonymousBuyer();
    }
}
