/*
package se.iths.auktionera.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.iths.auktionera.business.model.Account;
import se.iths.auktionera.business.model.AccountRequest;
import se.iths.auktionera.persistence.entity.AccountEntity;
import se.iths.auktionera.persistence.repo.AccountRepo;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
class AccountServiceTest {

    @MockBean
    AccountRepo accountRepo;

    private IAccountService accountService;
    private AccountEntity accountEntity;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepo, );
        accountEntity = AccountEntity.builder()
                .id(10)
                .anonymousBuyer(false)
                .userName("User")
                .city("City")
                .email("name@example.com")
                .postNr(12345)
                .streetName("Street 1")
                .userName("Usern")
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void getAccount() {
        when(accountRepo.findByuserName("User")).thenReturn(accountEntity);
        Account account = accountService.getAccount("User");
        assertNotNull(account);
        assertEquals(accountEntity.getUserName(), account.getUser().getUserName());
    }

    @Test
    void getAccountFirstSignIn() {
        when(accountRepo.findByuserName("User")).thenReturn(null);
        when(accountRepo.saveAndFlush(any(AccountEntity.class))).thenReturn(AccountEntity.builder().userName("User").createdAt(Instant.now()).build());
        Account account = accountService.getAccount("User");
        assertNotNull(account);
    }

    @Test
    void updateAccountUserName() {
        when(accountRepo.findByuserName("User")).thenReturn(accountEntity);
        when(accountRepo.saveAndFlush(any(AccountEntity.class))).thenReturn(accountEntity);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setUserName("NewName");
        Map<String, String> fields = Map.of("userName", "NewName");
        Account account = accountService.updateAccount("User", accountRequest);
        assertNotNull(account);
        assertEquals(fields.get("userName"), account.getUser().getUserName());
    }


}*/
