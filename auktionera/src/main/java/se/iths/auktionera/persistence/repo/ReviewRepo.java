package se.iths.auktionera.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.auktionera.persistence.entity.ReviewEntity;

public interface ReviewRepo extends JpaRepository<ReviewEntity, Long> {
}
