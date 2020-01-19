package se.iths.auktionera.business.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.iths.auktionera.persistence.entity.TagsEntity;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AuctionRequest {
    private List<String> tags = new ArrayList<>();
    private String category;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") // for example "2019-12-22T23:26:57.222Z"
    private Instant endsAt = Instant.now().plus(Duration.ofDays(3));

    private int startPrice;
    private int buyoutPrice;
    private int minBidStep;
    private DeliveryType deliveryType;
    private List<Image> images;
}
