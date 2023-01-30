package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    void saveAccount(Account account);

    List<Account> getAllAccounts();

    Account getAccountById(Long id);

    Account getAccountByNumber(String accNumber);

    void deleteAccount(Client client, Account account);
}
