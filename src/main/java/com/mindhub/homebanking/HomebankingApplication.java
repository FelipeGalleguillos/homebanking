package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	PasswordEncoder paswordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository cliRepository, AccountRepository accRepository, TransactionRepository tranRepository, LoanRepository loanRepository, ClientLoanRepository cliLoanRepository, CardRepository cardRepository){
		return (args) -> {

			// save a couple of customers
			Client client1 = new Client("Melba","Morel","melba@mindhub.com", paswordEncoder.encode("melba123"));
			Client client2 = new Client("Felipe","Galleguillos","felipe@gmail.com", paswordEncoder.encode("felipe123"));
			Account account1 = new Account("VIN0001", LocalDate.now(),5000,AccountType.SAVINGS);
			Account account2 = new Account("VIN0002", LocalDate.now().plusDays(1),5000,AccountType.CURRENT);
			Account account3 = new Account("VIN0003", LocalDate.now().minusDays(1),4800,AccountType.SAVINGS);
			Account account4 = new Account("VIN0004", LocalDate.now().plusDays(2),10000,AccountType.CURRENT);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT,300,"compras del supermercado",LocalDateTime.now());
			Transaction transaction2 = new Transaction(TransactionType.CREDIT,150,"compras del supermercado",LocalDateTime.now());
			Transaction transaction3 = new Transaction(TransactionType.DEBIT,250,"compras del supermercado",LocalDateTime.now());
			Transaction transaction4 = new Transaction(TransactionType.CREDIT,300,"compras del supermercado",LocalDateTime.now());

			Transaction transaction5 = new Transaction(TransactionType.DEBIT,1000,"compras del supermercado",LocalDateTime.now());
			Transaction transaction6 = new Transaction(TransactionType.CREDIT,150,"compras del supermercado",LocalDateTime.now());
			Transaction transaction7 = new Transaction(TransactionType.DEBIT,250,"compras del supermercado",LocalDateTime.now());
			Transaction transaction8 = new Transaction(TransactionType.CREDIT,110,"compras del supermercado",LocalDateTime.now());

			Card card1 = new Card(client1.getFirstName().concat(" "+client1.getLastName()),CardType.DEBIT,CardColor.GOLD,"3325-7557-1432-8653",564,LocalDate.now());
			Card card2 = new Card(client1.getFirstName().concat(" "+client1.getLastName()),CardType.CREDIT,CardColor.TITANIUM,"1286-6977-3546-4957",126,LocalDate.now());
			Card card3 = new Card(client2.getFirstName().concat(" "+client2.getLastName()),CardType.CREDIT,CardColor.SILVER,"3656-6787-1938-4432",208,LocalDate.now());

			Loan loan1 = new Loan("Hipotecario",500000,20);
			List<Integer> list = new ArrayList<>();
			list.add(12);list.add(24);list.add(36);list.add(48);list.add(60);
			loan1.setPayments(list);

			Loan loan2 = new Loan("Personal",100000,15);
			List<Integer> list1 = new ArrayList<>();
			list1.add(12);list1.add(6);list1.add(24);
			loan2.setPayments(list1);

			Loan loan3 = new Loan("Automotriz",300000,10);
			List<Integer> list2 = new ArrayList<>();
			list2.add(12);list2.add(6);list2.add(24);list2.add(36);
			loan3.setPayments(list2);

			ClientLoan cliloan1 = new ClientLoan(400000,60,LocalDate.now(),client1,loan1);
			ClientLoan cliloan2 = new ClientLoan(50000,12,LocalDate.now(),client1,loan2);
			ClientLoan cliloan3 = new ClientLoan(100000,24,LocalDate.now(),client2,loan2);
			ClientLoan cliloan4 = new ClientLoan(200000,36,LocalDate.now(),client2,loan3);



			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			client1.addAccount(account1);
			client1.addAccount(account2);
			client1.addLoan(cliloan1);
			client1.addLoan(cliloan2);
			client1.addCard(card1);
			client1.addCard(card2);
			client2.addAccount(account3);
			client2.addAccount(account4);
			client2.addLoan(cliloan3);
			client2.addLoan(cliloan4);
			client2.addCard(card3);

			cliRepository.save(client1);
			cliRepository.save(client2);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

			cliLoanRepository.save(cliloan1);
			cliLoanRepository.save(cliloan2);
			cliLoanRepository.save(cliloan3);
			cliLoanRepository.save(cliloan4);

			account1.setClient(client1);
			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account1.addTransaction(transaction4);
			account2.setClient(client1);
			account2.addTransaction(transaction5);
			account2.addTransaction(transaction6);
			account2.addTransaction(transaction7);
			account2.addTransaction(transaction8);
			account3.setClient(client2);
			account4.setClient(client2);

			accRepository.save(account1);
			accRepository.save(account2);
			accRepository.save(account3);
			accRepository.save(account4);

			tranRepository.save(transaction1);
			tranRepository.save(transaction2);
			tranRepository.save(transaction3);
			tranRepository.save(transaction4);
			tranRepository.save(transaction5);
			tranRepository.save(transaction6);
			tranRepository.save(transaction7);
			tranRepository.save(transaction8);

		};
	}

}