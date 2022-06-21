package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.Repositories.*;
import com.mindhub.HomeBanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomeBankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomeBankingApplication.class, args);

	}
//CommandLineRunner  interfaz con un solo método run()
	@Autowired//para que Spring inyecte el objeto PasswordEncoder que
	// se crea con el @Bean en la clase WebAuthentication
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,CardRepository cardRepository,
									  CreditCardLimitRepository creditCardLimitRepository) {
		return (args) -> {
			// save a couple of customers
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melbamorel123"));
			clientRepository.save(client1);
			Client client2 = new Client("Roberto", "Galati", "rgalati@mindhub.com", passwordEncoder.encode("1234"));
			clientRepository.save(client2);
			Client client3 = new Client("Marcos", "Senna", "markitosenna@mindhub.com", passwordEncoder.encode("Marcos123"));
			clientRepository.save(client3);
			Client admin = new Client("admin","admin","admin@admin.com", passwordEncoder.encode("admin"));
			clientRepository.save(admin);

			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000,client1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500,client1);
			accountRepository.save(account2);
			Account account3 = new Account("VIN003", LocalDateTime.now(), 6000,client2);
			accountRepository.save(account3);
			Account account4 = new Account("VIN004", LocalDateTime.now().plusDays(1), 8500,client2);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.DEBITO,LocalDateTime.now(),"description",-5000,account1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(TransactionType.CREDITO,LocalDateTime.now(),"description2",6000,account1);
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction(TransactionType.CREDITO,LocalDateTime.now(),"description3",10000,account2);
			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction(TransactionType.DEBITO,LocalDateTime.now(),"description4",-2000,account3);
			transactionRepository.save(transaction4);

			Loan loan1= new Loan("Hipotecario",500000.0, List.of(12,24,36,48,60));
			loanRepository.save(loan1);
			Loan loan2= new Loan("Personal",100000.0, List.of(6,12,24));
			loanRepository.save(loan2);
			Loan loan3= new Loan("Automotriz",300000.0, List.of(6,12,24,36));
			loanRepository.save(loan3);

			ClientLoan clientLoan1= new ClientLoan(loan1,400000.0,60,client1);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2= new ClientLoan(loan2,50000.0,12,client1);
			clientLoanRepository.save(clientLoan2);
			ClientLoan clientLoan3= new ClientLoan(loan2,100000.0,24,client2);
			clientLoanRepository.save(clientLoan3);
			ClientLoan clientLoan4= new ClientLoan(loan3,200000.0,36,client2);
			clientLoanRepository.save(clientLoan4);


			CardCreditLimit limitGold = new CardCreditLimit(CardColor.GOLD,10000,5000);
			creditCardLimitRepository.save(limitGold);

			CardCreditLimit limitSilver = new CardCreditLimit(CardColor.SILVER,20000,10000);
			creditCardLimitRepository.save(limitSilver);

			CardCreditLimit limitTitanium = new CardCreditLimit(CardColor.TITANIUM,30000,15000);
			creditCardLimitRepository.save(limitTitanium);

			CardCreditLimit limitDebit = new CardCreditLimit(50000);
			creditCardLimitRepository.save(limitDebit);

			Card card1= new Card(CardType.DEBIT,"Melba Morel",CardColor.GOLD,"4485-2483-7317-3815",233,LocalDateTime.now(),LocalDateTime.now().plusYears(5),client1,limitDebit);
			cardRepository.save(card1);
			Card card2= new Card(CardType.CREDIT,"Melba Morel",CardColor.TITANIUM,"4929-3924-7470-2017",322,LocalDateTime.now(),LocalDateTime.now().plusYears(5),client1,limitTitanium);
			cardRepository.save(card2);
			Card card3= new Card(CardType.CREDIT,"Roberto Galati",CardColor.SILVER,"4532-9214-7295-3831",333,LocalDateTime.now(),LocalDateTime.now().plusYears(5),client2,limitSilver);
			cardRepository.save(card3);


		};
	}

}