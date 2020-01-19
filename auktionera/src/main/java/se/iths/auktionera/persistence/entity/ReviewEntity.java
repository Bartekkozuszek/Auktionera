package se.iths.auktionera.persistence.entity;

import lombok.*;
import se.iths.auktionera.business.model.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity {

    @Id
    @Column(name = "reviewId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @OneToOne
    private AccountEntity seller;

    @OneToOne
    private AccountEntity buyer;

    private Instant createdAt;
    private Instant lastEditAt;
    private Double rating;
    private String reviewText;

    @ManyToMany(mappedBy = "reviewEntities")
    private Set<UserStatsEntity> userStats = new HashSet<>();
}