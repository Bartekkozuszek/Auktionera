package se.iths.auktionera.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userName;

    private String email = StringUtils.EMPTY;
    private boolean anonymousBuyer;
    private Instant createdAt;
    private String streetName = StringUtils.EMPTY;
    private int postNr;
    private String city = StringUtils.EMPTY;

    @JsonIgnore
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AuctionEntity> auctionEntities = new ArrayList<>();

    @OneToMany
    private Set<ReviewEntity> reviewEntities = new HashSet<>();

    @OneToOne
    private UserStatsEntity userStats;
}
