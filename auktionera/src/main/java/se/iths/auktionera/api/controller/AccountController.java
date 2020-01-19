package se.iths.auktionera.api.controller;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;
import se.iths.auktionera.business.model.Account;
import se.iths.auktionera.business.model.AccountRequest;
import se.iths.auktionera.business.model.Auction;
import se.iths.auktionera.business.service.IAccountService;
import se.iths.auktionera.business.service.IAuctionService;
import se.iths.auktionera.business.service.RabbitMQSender;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {

    private final IAccountService accountService;

    private final IAuctionService auctionService;

    private final RabbitMQSender rabbitMQSender;

    public AccountController(IAccountService accountService, IAuctionService auctionService, RabbitMQSender rabbitMQSender) {
        this.accountService = accountService;
        this.auctionService = auctionService;
        this.rabbitMQSender = rabbitMQSender;
    }

/*    @PostMapping(value = "api/rabbitmq")
    public String producer(@RequestParam(value = "message") String message, HttpServletRequest request) throws IOException {
        rabbitMQSender.send(message, accountService.getAccount((String) request.getAttribute("userName")).getUser().getUserName());
        return "Message sent to the RabbitMQ successfully " +
                accountService.getAccount((String) request.getAttribute("userName")).getUser().getUserName();
    }*/

    @GetMapping("api/account")
    public Account getAccount(HttpServletRequest request) {
        String userName = accountService.getAccount(request.getUserPrincipal().getName()).getUser().getUserName();
        Long userId = accountService.getAccount(request.getUserPrincipal().getName()).getUser().getId();
        rabbitMQSender.send(userName, userId);
        return accountService.getAccount(request.getUserPrincipal().getName());
    }

    @PatchMapping("api/account")
    public Account updateAccount(@Valid @RequestBody AccountRequest fields, HttpServletRequest request) {
        return accountService.updateAccount(request.getUserPrincipal().getName(), fields);
    }

    @GetMapping("api/account/auctions")
    public List<Auction> getAllAuctionsForAccount(@RequestParam Map<String, String> filter, @RequestParam Map<String, String> sort, HttpServletRequest request) {
        return auctionService.getAuctionsForOneAccount(filter, sort, request.getUserPrincipal().getName());
    }
}
