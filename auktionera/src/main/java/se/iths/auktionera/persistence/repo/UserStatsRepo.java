package se.iths.auktionera.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.auktionera.persistence.entity.UserStatsEntity;

public interface UserStatsRepo extends JpaRepository<UserStatsEntity, Long> {
}
