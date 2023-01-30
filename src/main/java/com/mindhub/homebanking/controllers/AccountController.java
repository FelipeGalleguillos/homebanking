package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import org.springframework.security.core.Authentication;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAllAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @GetMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO(accountService.getAccountById(id));
    }

    @DeleteMapping(path = "/accounts/{id}")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @PathVariable Long id){
        if(accountService.getAccountById(id).getBalance()>0){
            return new ResponseEntity<>("can't delete account",HttpStatus.FORBIDDEN);
        }
        accountService.deleteAccount(clientService.getClientByEmail(authentication.getName()),accountService.getAccountById(id));
        return new ResponseEntity<>("deleted",HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/clients/current/accounts/{accountType}")
    public ResponseEntity<Object> createAccount(Authentication authentication, @PathVariable String accountType) {
        Client client = clientService.getClientByEmail(authentication.getName());
        AccountType type = null;

        if (client.getAccounts().stream().toArray().length >= 3) {
            return new ResponseEntity<>("can't create another account", HttpStatus.FORBIDDEN);
        }

        if(accountType.equals("CORRIENTE")){
            type = AccountType.CORRIENTE;
        }else{
            type = AccountType.AHORRO;
        }

        String number = "VIN" + ((int) ((Math.random() * (999999 - 100000)) + 100000));
        Account account = new Account(number, LocalDate.now(), 0,type);
        client.addAccount(account);
        account.setClient(client);
        clientService.saveClient(client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
