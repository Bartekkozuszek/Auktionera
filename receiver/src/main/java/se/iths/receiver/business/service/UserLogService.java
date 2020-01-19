package se.iths.receiver.business.service;

import org.springframework.stereotype.Service;
import se.iths.receiver.persistence.entity.UserLogEntity;
import se.iths.receiver.persistence.repo.UserLogRepo;

import java.time.Instant;

@Service
public class UserLogService implements IUserLogService{

    private final UserLogRepo userLogRepo;

    public UserLogService(UserLogRepo userLogRepo) {
        this.userLogRepo = userLogRepo;
    }

    @Override
    public void createUserLog(String userName, String userId) {
        UserLogEntity userLogEntity = new UserLogEntity();
        userLogEntity.setUserName(userName);
        userLogEntity.setUserId(userId);
        userLogEntity.setLoginStamp(Instant.now());
        userLogRepo.saveAndFlush(userLogEntity);
    }
}
