package se.iths.auktionera.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.iths.auktionera.business.model.User;
import se.iths.auktionera.persistence.entity.AccountEntity;
import se.iths.auktionera.persistence.repo.AccountRepo;
import se.iths.auktionera.persistence.repo.AuctionRepo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
class UserServiceTest {

    @MockBean
    AccountRepo accountRepo;
    AuctionRepo auctionRepo;

    private IUserService userService;
    private AccountEntity entity1, entity2;
    private List<AccountEntity> entityList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        userService = new UserService(accountRepo, auctionRepo);
        entity1 = AccountEntity.builder()
                .id(10)
                .anonymousBuyer(false)
                .city("City")
                .email("name@example.com")
                .postNr(12345)
                .streetName("Street 1")
                .userName("Usern")
                .createdAt(Instant.now())
                .build();

        entity2 = AccountEntity.builder()
                .id(5)
                .anonymousBuyer(false)
                .city("Town")
                .email("mail@example.com")
                .postNr(23456)
                .streetName("Street 5")
                .userName("kim")
                .createdAt(Instant.now())
                .build();

        entityList.add(entity1);
        entityList.add(entity2);
    }

    @Test
    void getUsersWithoutFilterOrSort() {
        when(accountRepo.findAll()).thenReturn(entityList);

        List<User> userList = userService.getUsers(null, null);
        assertNotNull(userList);
        assertEquals(2, userList.size());
        assertSame(userList.get(1).getClass(), User.class);
        assertThat(userList.get(0).getUserName(), is(entity1.getUserName()));
        assertThat(userList.get(0).getId(), is(entity1.getId()));
        assertThat(userList.get(1).getId(), is(entity2.getId()));
        assertThat(userList.get(1).getUserName(), is(entity2.getUserName()));
    }

    @Test
    void getUserById() {
        when(accountRepo.findById(10L)).thenReturn(java.util.Optional.ofNullable(entity1));

        User user = userService.getUserById(10L);
        assertNotNull(user);
        assertThat(user.getUserName(), is(entity1.getUserName()));
        assertThat(user.getId(), is(10L));
        assertThat(user.getCreatedAt(), is(entity1.getCreatedAt()));
    }

}