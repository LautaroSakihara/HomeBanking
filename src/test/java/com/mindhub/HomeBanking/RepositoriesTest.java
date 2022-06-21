package com.mindhub.HomeBanking;

import com.mindhub.HomeBanking.Repositories.*;
import com.mindhub.HomeBanking.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


    @SpringBootTest

    @AutoConfigureTestDatabase(replace = NONE)

    public class RepositoriesTest {

        @Autowired

        LoanRepository loanRepository;

        @Test

        public void existLoans(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans,is(not(empty())));

        }
        @Test

        public void existPersonalLoan(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

        }

        @Test

        public void existAutomotrizLoan(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans, hasItem(hasProperty("name", is("Automotriz"))));

        }
        @Test
        public void existHipotecarioLoan(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans, hasItem(hasProperty("name", is("Hipotecario"))));

        }

        @Autowired

        ClientRepository clientRepository;

        @Test
        public void existClients(){

            List<Client> clients = clientRepository.findAll();

            assertThat(clients,is(not(empty())));

        }
        @Test
        public void existFirstNameClients(){

            List<Client> clients = clientRepository.findAll();

            assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));

        }
        @Autowired

        TransactionRepository transactionRepository;
        @Test
        public void existTransactions(){

            List<Transaction> transactions = transactionRepository.findAll();

            assertThat(transactions,is(not(empty())));

        }

        @Test
        public void existTypeTransaction(){

            List<Transaction> transactions = transactionRepository.findAll();

            assertThat(transactions, hasItem(hasProperty("type")));

        }
        @Autowired

        AccountRepository accountRepository;
        @Test
        public void existAccounts(){

            List<Account> accounts = accountRepository.findAll();

            assertThat(accounts,is(not(empty())));

        }

        @Test
        public void existNumberAccounts(){

            List<Account> accounts = accountRepository.findAll();

            assertThat(accounts,hasItem(hasProperty("number")));

        }

        @Autowired

        CardRepository cardRepository;
        @Test
        public void existCards(){

            List<Card> cards = cardRepository.findAll();

            assertThat(cards,is(not(empty())));

        }

        @Test
        public void existNumberCards(){

            List<Card> cards = cardRepository.findAll();

            assertThat(cards,hasItem(hasProperty("number")));

        }

    }

