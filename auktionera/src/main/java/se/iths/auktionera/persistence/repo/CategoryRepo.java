package se.iths.auktionera.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.auktionera.persistence.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryTitle(String category);
}
