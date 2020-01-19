package se.iths.auktionera.business.service;

import se.iths.auktionera.business.model.Auction;
import se.iths.auktionera.business.model.AuctionRequest;
import se.iths.auktionera.business.model.Bid;

import java.util.List;
import java.util.Map;

public interface IAuctionService {
    List<Auction> getAuctions(Map<String, String> filters, Map<String, String> sorters);

    Auction getAuctionById(long id);

    Auction createAuction(String userName, AuctionRequest auctionRequest);

    void deleteAuctionById(Long id, String userName);

    Auction addBidToAuction(Bid bid, Long id, String userName);

    List<Auction> getAuctionsForOneAccount(Map<String, String> filters, Map<String, String> sorters, String userName);
}
