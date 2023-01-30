package com.mindhub.homebanking.Services.Impl;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
    @Override
    public Account getAccountByNumber(String accNumber) {
        return accountRepository.findByNumber(accNumber);
    }

    @Override
    public void deleteAccount(Client client, Account account) {
        Set<Account> accounts = client.getAccounts();
        accounts.remove(account);
        client.setAccounts(accounts);
        clientRepository.save(client);
        accountRepository.delete(account);
    }
}
