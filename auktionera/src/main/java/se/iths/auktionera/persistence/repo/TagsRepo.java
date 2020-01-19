package se.iths.auktionera.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.auktionera.persistence.entity.TagsEntity;

public interface TagsRepo extends JpaRepository<TagsEntity, Long> {
}
