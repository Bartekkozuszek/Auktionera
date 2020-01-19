package se.iths.receiver.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.receiver.persistence.entity.UserLogEntity;

public interface UserLogRepo extends JpaRepository<UserLogEntity, Long> {
}
