package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.Client;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO(){}

    public ClientDTO(Client client){

        this.id = client.getId();

        this.firstName = client.getFirstName();

        this.lastName = client.getLastName();

        this.email = client.getEmail();

        this.password = client.getPassword();

        this.accounts = client.getAccounts().stream().filter(account -> account.isActive()==true).map(account -> new AccountDTO(account)).collect(toSet());

        this.loans = client.getClientLoan().stream().map(clientLoan ->new ClientLoanDTO(clientLoan)).collect(toSet());

        this.cards=client.getCards().stream().filter(card -> card.isActive()==true).map(CardDTO::new).collect(toSet());
    }

    public long getId(){return id;}

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<AccountDTO> getAccount() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
