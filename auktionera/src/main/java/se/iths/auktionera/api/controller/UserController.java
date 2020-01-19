package se.iths.auktionera.api.controller;

import org.springframework.web.bind.annotation.*;
import se.iths.auktionera.business.model.Auction;
import se.iths.auktionera.business.model.User;
import se.iths.auktionera.business.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public List<User> getUsers(@RequestParam Map<String, String> filter, @RequestParam Map<String, String> sort) {
        return userService.getUsers(filter, sort);
    }

    @GetMapping("/api/users/{id}")
    public User getOneUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/api/users/{id}/auctions")
    public List<Auction> getAuctionsByUser(@RequestParam Map<String, String> filter, @RequestParam Map<String, String> sort, HttpServletRequest request, @PathVariable Long id) {
        return userService.getAuctionsByUser(filter, sort, id);
    }
}
