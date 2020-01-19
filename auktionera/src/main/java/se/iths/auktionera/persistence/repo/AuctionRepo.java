package se.iths.auktionera.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import se.iths.auktionera.business.model.AuctionState;
import se.iths.auktionera.persistence.entity.AccountEntity;
import se.iths.auktionera.persistence.entity.AuctionEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AuctionRepo extends JpaRepository<AuctionEntity, Long>, JpaSpecificationExecutor {
    List<AuctionEntity> findAuctionsBySeller(AccountEntity seller);
    List<AuctionEntity> findAllByAuctionState(AuctionState auctionState);
    Optional<AuctionEntity> findByIdAndAuctionState(Long id, AuctionState state);
    List<AuctionEntity> findByAuctionStateAndEndsAtIsGreaterThan(AuctionState state, Instant time);
    List<AuctionEntity> findByAuctionStateAndEndsAtIsLessThanEqual(AuctionState state, Instant time);
}
