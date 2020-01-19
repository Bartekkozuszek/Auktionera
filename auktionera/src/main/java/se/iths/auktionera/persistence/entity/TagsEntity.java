package se.iths.auktionera.persistence.entity;

        import lombok.*;

        import javax.persistence.*;
        import java.util.HashSet;
        import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String tag;

    @ManyToMany(mappedBy = "tags")
    private Set<AuctionEntity> auctions = new HashSet<>();
}
