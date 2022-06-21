package com.mindhub.HomeBanking.Controllers;

import com.mindhub.HomeBanking.Repositories.CardRepository;
import com.mindhub.HomeBanking.Services.AccountService;
import com.mindhub.HomeBanking.Services.CardService;
import com.mindhub.HomeBanking.Services.ClientService;
import com.mindhub.HomeBanking.Services.TransactionService;
import com.mindhub.HomeBanking.dtos.PaymentDTO;
import com.mindhub.HomeBanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.mindhub.HomeBanking.models.TransactionType.CREDITO;
import static com.mindhub.HomeBanking.models.TransactionType.DEBITO;

@RestController
@RequestMapping("/api")

public class TransactionController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;


    @Transactional
    @PostMapping("/transactions")

    public ResponseEntity<Object> Transaction( Authentication authentication,

            @RequestParam Double amount, @RequestParam String description, @RequestParam String numberOwn,

            @RequestParam String numberDestiny) {

        Client currentClient = clientService.getClientCurrent(authentication);
        Account AccountOrigin = accountService.getAccountByNumber(numberOwn);
        Account AccountDestiny = accountService.getAccountByNumber(numberDestiny);

        if (amount <= 0 || amount == null || amount.isInfinite() || amount.isInfinite()) {

            return new ResponseEntity<>("Missing amount", HttpStatus.FORBIDDEN);

        }
        if (description.isEmpty()) {

            return new ResponseEntity<>("Missing Description", HttpStatus.FORBIDDEN);

        }
        if (numberOwn.isEmpty()) {

            return new ResponseEntity<>("Missing Origin Number Account", HttpStatus.FORBIDDEN);

        }
        if (numberDestiny.isEmpty()) {

            return new ResponseEntity<>("Missing Destiny Number Account", HttpStatus.FORBIDDEN);

        }

        if (numberOwn.equals(numberDestiny)) {

            return new ResponseEntity<>("Same account number!", HttpStatus.FORBIDDEN);

        }
        if (AccountOrigin == null) {

            return new ResponseEntity<>("Account Origin does not exist", HttpStatus.FORBIDDEN);

        }
        if(!currentClient.getAccounts().contains(AccountOrigin)){

        return new ResponseEntity<>("Not your Account", HttpStatus.FORBIDDEN);

        }
        if(AccountDestiny == null){

            return new ResponseEntity<>("Account Destination does not exist", HttpStatus.FORBIDDEN);

        }

        if (AccountOrigin.getBalance() < amount){

            return new ResponseEntity<>("Amount not available", HttpStatus.FORBIDDEN);
        }

        Transaction transaction1 = new Transaction(DEBITO, LocalDateTime.now(),description +" " +numberDestiny ,(amount)*-1,AccountOrigin);
        transactionService.saveTransaction(transaction1);

        Transaction transaction2 = new Transaction(CREDITO, LocalDateTime.now(),description + " " + numberOwn ,amount, AccountDestiny);
        transactionService.saveTransaction(transaction2);


        AccountOrigin.setBalance(AccountOrigin.getBalance()-amount);
        accountService.saveAccount(AccountOrigin);

        AccountDestiny.setBalance(AccountDestiny.getBalance()+amount);
        accountService.saveAccount(AccountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @Transactional
    @PatchMapping("/cardTransaction")

    public ResponseEntity<Object> newTransaction( Authentication authentication,@RequestBody PaymentDTO paymentDTO){
        Account ownAccount = accountService.getAccountByNumber(paymentDTO.getAccountNumber());
        Card currentCard = cardRepository.findByNumber(paymentDTO.getCardNumber());

        if (currentCard.getType() == CardType.CREDIT) {
            if (paymentDTO.getCardNumber().isEmpty() || paymentDTO.getCardHolder().isEmpty() || paymentDTO.getAccountNumber().isEmpty())
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            if (currentCard.isActive() == false) {
                return new ResponseEntity<>("This card is inactive", HttpStatus.FORBIDDEN);
            }
            if (paymentDTO.getAmount() > currentCard.getCardCreditLimit().getAvailableBalance()) {
                return new ResponseEntity<>("Not enough funds", HttpStatus.FORBIDDEN);
            }
            if (paymentDTO.getAmount() <= 0){
                return new ResponseEntity<>("Not a valid amount", HttpStatus.FORBIDDEN);
            }
            if (currentCard.getCvv() != paymentDTO.getCvv()){
                return new ResponseEntity<>("Incorrect cvv number", HttpStatus.FORBIDDEN);
            }
            if (currentCard.getExpirationDate().isBefore(LocalDateTime.now())){
                return new ResponseEntity<>("This Card has expired", HttpStatus.FORBIDDEN);
            }
            if (currentCard.getNumber() == null){
                return new ResponseEntity<>("This card does not exist", HttpStatus.FORBIDDEN);
            }
            if (!currentCard.getCardHolder().equals(paymentDTO.getCardHolder())){
                return new ResponseEntity<>("Incorrect card holder", HttpStatus.FORBIDDEN);
            }

            Transaction debit = new Transaction(paymentDTO.getDescription(),paymentDTO.getAmount(),LocalDateTime.now(),TransactionType.DEBITO,currentCard);
            transactionService.saveTransaction(debit);

            currentCard.getCardCreditLimit().setAvailableBalance(currentCard.getCardCreditLimit().getAvailableBalance()-paymentDTO.getAmount());
            cardService.saveCard(currentCard);
        }
        if (currentCard.getType() == CardType.DEBIT){
            if (paymentDTO.getAmount() <= 0){
                return new ResponseEntity<>("Not a valid amount", HttpStatus.FORBIDDEN);
            }
            if (paymentDTO.getAmount() > ownAccount.getBalance()){
                return new ResponseEntity<>("You exceeded the max amount", HttpStatus.FORBIDDEN);
            }
            if (currentCard.getCvv() != paymentDTO.getCvv()) {
                return new ResponseEntity<>("Incorrect cvv number", HttpStatus.FORBIDDEN);
            }
            if (paymentDTO.getDescription().isEmpty()){
                return new ResponseEntity<>("Description is empty", HttpStatus.FORBIDDEN);
            }

            Transaction debit = new Transaction(paymentDTO.getDescription(),paymentDTO.getAmount(),LocalDateTime.now(),TransactionType.DEBITO,currentCard);
            transactionService.saveTransaction(debit);
            ownAccount.setBalance(ownAccount.getBalance() - paymentDTO.getAmount());
            accountService.saveAccount(ownAccount);
        }
        return new ResponseEntity<>("Transaction successful", HttpStatus.CREATED);
    }
}

