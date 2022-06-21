package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.Account;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;

    private String number;
    private String creationDate;
    private double balance;

    private Set<TransactionDTO> transactions;

    public AccountDTO() {}

    public AccountDTO(Account account){
        this.id = account.getId();

        this.number = account.getNumber();

        this.creationDate = account.getCreationDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

        this.balance = account.getBalance();

        this.transactions = account.getTransactions().stream().filter(transaction -> transaction.isActive()==true).map(TransactionDTO::new).collect(Collectors.toSet());
    }
    public long getId() {
        return id;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

