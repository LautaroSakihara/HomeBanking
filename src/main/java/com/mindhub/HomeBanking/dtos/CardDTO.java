package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.Card;
import com.mindhub.HomeBanking.models.CardColor;
import com.mindhub.HomeBanking.models.CardType;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class CardDTO {
    private long id;

    private CardType type;

    private CardColor color;

    private String number;

    private int cvv;

    private String creationDate;

    private String expirationDate;

    private String cardHolder;

    private Set<TransactionDTO> transactions = new HashSet<>();

    public CardDTO() {}

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.creationDate = card.getCreationDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        this.expirationDate = card.getExpirationDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        this.transactions = card.getTransactions().stream().filter(card1 -> card.isActive()==true).map(transaction -> new TransactionDTO(transaction)).collect(toSet());

    }

    public long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }


    public CardColor getColor() {
        return color;
    }


    public String getNumber() {
        return number;
    }


    public int getCvv() {
        return cvv;
    }


    public String getCreationDate() {
        return creationDate;
    }


    public String getExpirationDate() {
        return expirationDate;
    }


    public String getCardHolder() {
        return cardHolder;
    }


    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }


}
