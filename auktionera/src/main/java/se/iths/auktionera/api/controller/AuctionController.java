package se.iths.auktionera.api.controller;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.iths.auktionera.api.IncomingQueryEvent;
import se.iths.auktionera.business.model.*;
import se.iths.auktionera.business.service.IAuctionService;
import se.iths.auktionera.business.service.IReviewService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class AuctionController {

    private final IAuctionService auctionService;
    private final IReviewService reviewService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AuctionController(IAuctionService auctionService, IReviewService reviewService, ApplicationEventPublisher applicationEventPublisher) {
        this.auctionService = auctionService;
        this.reviewService = reviewService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping("api/auctions")
    public List<Auction> getAuctions(@RequestParam Map<String, String> filter, @RequestParam Map<String, String> sort) {
        IncomingQueryEvent event = new IncomingQueryEvent(this);
        applicationEventPublisher.publishEvent(event);
        return auctionService.getAuctions(filter, sort);
    }

    @GetMapping("api/auctions/{id}")
    public Auction getAuctionById(@PathVariable Long id) {
        return auctionService.getAuctionById(id);
    }

    @PostMapping("api/auctions")
    public Auction createAuction(@RequestBody AuctionRequest fields, HttpServletRequest request) {
        return auctionService.createAuction(request.getUserPrincipal().getName(), fields);
    }

    @PostMapping("api/auctions/{id}/bid")
    public Auction createBid(@RequestBody Bid bid, @PathVariable Long id, HttpServletRequest request) {
        return auctionService.addBidToAuction(bid, id, request.getUserPrincipal().getName());
    }

    @DeleteMapping("api/auctions/{id}")
    public void deleteAuction(@PathVariable Long id, HttpServletRequest request) {
        auctionService.deleteAuctionById(id, request.getUserPrincipal().getName());
    }

    @PostMapping("api/auctions/{id}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReview(@RequestBody ReviewRequest reviewRequest, @PathVariable Long id, HttpServletRequest request){
        reviewService.createReview(reviewRequest, id, request.getUserPrincipal().getName());
    }
}
