package se.iths.auktionera.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.iths.auktionera.api.exception.ExistingUsernameException;
import se.iths.auktionera.business.model.UserDTO;
import se.iths.auktionera.persistence.entity.AccountEntity;
import se.iths.auktionera.persistence.entity.UserStatsEntity;
import se.iths.auktionera.persistence.repo.AccountRepo;
import se.iths.auktionera.persistence.repo.UserStatsRepo;

import java.time.Instant;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private UserStatsRepo userStatsRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity user = accountRepo.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        return new User(user.getUserName(), user.getPassword(),
                new ArrayList<>());
    }

    public AccountEntity save(UserDTO user) throws ExistingUsernameException {
        if (accountRepo.findByUserName(user.getUsername()) == null) {
            AccountEntity newUser = new AccountEntity();
            newUser.setUserName(user.getUsername());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            newUser.setCreatedAt(Instant.now());
            UserStatsEntity use = new UserStatsEntity();
            newUser.setUserStats(use);
            userStatsRepo.saveAndFlush(use);
            accountRepo.saveAndFlush(newUser);
            return newUser;
        } else {
            throw new ExistingUsernameException("This username already exists. Try another.");
        }
    }
}
