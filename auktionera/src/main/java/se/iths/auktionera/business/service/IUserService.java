package se.iths.auktionera.business.service;

import se.iths.auktionera.business.model.Auction;
import se.iths.auktionera.business.model.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

    List<User> getUsers(Map<String, String> filters, Map<String, String> sorters);

    User getUserById(Long id);

    List<Auction> getAuctionsByUser(Map<String, String> filters, Map<String, String> sorters, Long id);
}
