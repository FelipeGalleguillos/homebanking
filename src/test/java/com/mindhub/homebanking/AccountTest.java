package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class AccountTest {

//    @Test
//    public void addtransactionToAccount(){
//        Account account = new Account();
//        Transaction transaction = new Transaction();
//        account.addTransaction(transaction);
//        List<Transaction> transactions = account.getTransactions().stream().collect(Collectors.toList());
//
//        assertThat(transactions,is(not(empty())));
//    }
//
//    @Test
//    public void updatedAccountBalance(){
//        Account account = new Account();
//        account.setBalance(0);
//        Transaction transaction = new Transaction();
//        transaction.setAmount(1000);
//        transaction.setType(TransactionType.CREDITO);
//        account.addTransaction(transaction);
//
//        assertThat(account.getBalance(),is(1000.0));
//    }
}
