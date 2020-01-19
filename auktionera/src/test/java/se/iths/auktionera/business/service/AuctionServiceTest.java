package se.iths.auktionera.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import se.iths.auktionera.business.model.Auction;
import se.iths.auktionera.business.model.AuctionRequest;
import se.iths.auktionera.business.model.AuctionState;
import se.iths.auktionera.business.model.DeliveryType;
import se.iths.auktionera.persistence.entity.AccountEntity;
import se.iths.auktionera.persistence.entity.AuctionEntity;
import se.iths.auktionera.persistence.repo.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
class AuctionServiceTest {

    @MockBean
    AuctionRepo auctionRepo;

    @MockBean
    AccountRepo accountRepo;

    @MockBean
    UserStatsRepo userStatsRepo;

    @MockBean
    CategoryRepo categoryRepo;

    @MockBean
    TagsRepo tagsRepo;

    private IAuctionService auctionService;
    private AuctionEntity auctionEntity1;
    private AuctionRequest auctionRequest;
    private AccountEntity accountEntity;
    private List<AuctionEntity> auctions = new ArrayList<>();

    @BeforeEach
    void setUp() {

        auctionService = new AuctionService(auctionRepo, accountRepo, userStatsRepo, categoryRepo, tagsRepo);
        accountEntity = AccountEntity.builder()
                .id(10)
                .anonymousBuyer(false)
                .city("City")
                .email("name@example.com")
                .postNr(12345)
                .streetName("Street 1")
                .userName("Usern")
                .createdAt(Instant.now())
                .build();

        auctionEntity1 = AuctionEntity.builder()
                .seller(accountEntity)
                .description("Laptop")
                .endsAt(Instant.parse("2019-12-13T10:15:30Z"))
                .startPrice(100)
                .buyOutPrice(200)
                .minBidStep(10)
                .auctionState(AuctionState.INPROGRESS)
                .deliveryType(DeliveryType.PICKUPATADDRESS)
                .build();

        AuctionEntity auctionEntity2 = AuctionEntity.builder()
                .seller(accountEntity)
                .description("Nikes")
                .endsAt(Instant.parse("2019-12-13T10:15:30Z"))
                .startPrice(1000)
                .buyOutPrice(2000)
                .minBidStep(100)
                .auctionState(AuctionState.ENDEDBOUGHT)
                .deliveryType(DeliveryType.PICKUPATADDRESS)
                .build();

        auctionRequest = new AuctionRequest();
        auctionRequest.setDescription("Electronics");
        auctionRequest.setEndsAt(Instant.parse("2019-12-13T10:15:30Z"));
        auctionRequest.setStartPrice(100);
        auctionRequest.setBuyoutPrice(200);
        auctionRequest.setMinBidStep(10);
        auctionRequest.setDeliveryType(DeliveryType.PICKUPATADDRESS);

        auctions.add(auctionEntity1);
        //auctions.add(auctionEntity2);
    }

    @Test
    void shouldReturnAuctionsInProgress() {
        when(auctionRepo.findAllByAuctionState(any())).thenReturn(auctions);

        List<Auction> ongoingAuctions = auctionService.getAuctions(null, null);
        assertThat(ongoingAuctions.size(), is(1));
        assertThat(ongoingAuctions.get(0).getAuctionState(), is(AuctionState.INPROGRESS));
    }

/*    @Test
    void createAuction() {
        //when(auctionRepo.saveAndFlush(any(AuctionEntity.class))).thenReturn(auctionEntity);
        when(accountRepo.findByUserName(any())).thenReturn(accountEntity);

        Auction auctionAdded = auctionService.createAuction(accountEntity.getuserName(), auctionRequest);
        assertNotNull(auctionAdded);
        assertThat(auctionAdded.getDescription(), is("Electronics"));
        assertThat(auctionAdded.getStartPrice(), is(100));
        assertThat(auctionAdded.getSeller().getId(), is(10L));
    }*/
}
