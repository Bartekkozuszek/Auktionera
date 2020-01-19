package se.iths.auktionera.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.auktionera.business.model.Account;
import se.iths.auktionera.business.model.Auction;
import se.iths.auktionera.business.model.User;
import se.iths.auktionera.business.service.IAuctionService;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", roles = "USER")
class AuctionControllerTest {

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private IAuctionService auctionService;

    @Test
    void getAuctions(@Autowired MockMvc mvc) throws Exception {
        User seller = User.builder().id(1).userName("testName").build();
        Auction auction = Auction.builder().seller(seller).description("En stol").build();

        when(auctionService.getAuctions(any(), any())).thenReturn(List.of(auction));
        mvc.perform(get("/api/auctions").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'description':'En stol'}]"))
                .andExpect(content().json("[{'seller':{'userName':'testName'}}]"));

        verify(auctionService, times(1)).getAuctions(any(), any());
    }

    @Test
    void getAuctionById(@Autowired MockMvc mvc) throws Exception {
        User seller = User.builder().id(1).userName("testName").build();
        Auction auction = Auction.builder().seller(seller).description("En stol").build();

        when(auctionService.getAuctionById(1L)).thenReturn(auction);
        mvc.perform(get("/api/auctions/1").contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(auctionService, times(1)).getAuctionById(1L);
    }

    @Test
    void createAuction(@Autowired MockMvc mvc) throws Exception {
        Auction auction = Auction.builder()
                .description("Wonderful table")
                .build();
        String jsonBody = "    {\n" +
                "        \"description\" : \"Wonderful table\",\n" +
                "\t\t\t\t\"tags\" : \"furniture\",\n" +
                "\t\t\t\t\"endsAt\" : \"2019-12-15T18:00:00.000+02:00\",\n" +
                "        \"startPrice\" : 100,\n" +
                "        \"buyoutPrice\" : 200,\n" +
                "        \"minBidStep\" : 10,\n" +
                "        \"deliveryType\" : \"PICKUPATSELLERHOME\"\n" +
                "    }";

        when(auctionService.createAuction(anyString(), any())).thenReturn(auction);
        mvc.perform(post("/api/auctions").with(csrf()).characterEncoding("utf-8")
                .contentType(APPLICATION_JSON).content(jsonBody).accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(auctionService, times(1)).createAuction(anyString(), any());
    }
}