package se.iths.auktionera.persistence.entity;

import lombok.*;
import se.iths.auktionera.business.model.AuctionState;
import se.iths.auktionera.business.model.DeliveryType;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "auctions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @Column(updatable = false)
    private Set<TagsEntity> tags = new HashSet<>();

    private String description;

    @ManyToOne
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private AccountEntity seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private AccountEntity buyer;

    @Enumerated(EnumType.STRING)
    private AuctionState auctionState;

    private Instant endsAt;
    private Instant createdAt;
    private Instant currentBidAt;
    private Instant endedAt;
    private int startPrice;
    private int buyOutPrice;
    private int minBidStep;
    private int currentBid;
    private long latestBidder;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
}






