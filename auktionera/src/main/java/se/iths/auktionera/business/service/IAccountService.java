package se.iths.auktionera.business.service;

import se.iths.auktionera.business.model.Account;
import se.iths.auktionera.business.model.AccountRequest;

import java.util.Map;

public interface IAccountService {

    Account getAccount(String userName);
    Account updateAccount(String userName, AccountRequest accountRequest);
}
