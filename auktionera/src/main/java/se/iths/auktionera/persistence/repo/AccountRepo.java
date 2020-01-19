package se.iths.auktionera.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.auktionera.persistence.entity.AccountEntity;
import se.iths.auktionera.persistence.entity.AuctionEntity;

import java.util.List;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUserName(String username);
}
