package se.iths.auktionera.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String categoryTitle;

    @OneToMany
    private List<AuctionEntity> auctions;
}
