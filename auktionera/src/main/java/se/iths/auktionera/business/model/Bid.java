package se.iths.auktionera.business.model;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    private int bid;
    private boolean isValidBid;
    private Integer startPrice;
    private Integer buyOutPrice;
    private Integer minBidStep;
    private Integer currentBid;
}
