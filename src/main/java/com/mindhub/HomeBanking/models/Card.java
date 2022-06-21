package com.mindhub.HomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private boolean active;
    private CardType type;

    private CardColor color;

    private String number;
    private int cvv;

    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;

    private String cardHolder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "card", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cardLimit_id")
    private CardCreditLimit cardCreditLimit;



    public Card(){}

    public Card(CardType type,String cardHolder, CardColor color,String number, int cvv,LocalDateTime creationDate,LocalDateTime expirationDate, Client client,CardCreditLimit cardCreditLimit) {
        this.type = type;
        this.cardHolder = cardHolder;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.client = client;
        this.cardCreditLimit = cardCreditLimit;
        this.active = true;
;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardCreditLimit getCardCreditLimit() {
        return cardCreditLimit;
    }

    public void setCardCreditLimit(CardCreditLimit cardCreditLimit) {
        this.cardCreditLimit = cardCreditLimit;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
