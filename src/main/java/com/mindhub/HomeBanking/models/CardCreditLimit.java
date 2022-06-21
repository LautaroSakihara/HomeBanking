package com.mindhub.HomeBanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
public class CardCreditLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private CardColor cardColor;

   private double cardLimit,availableBalance;


    @OneToOne(mappedBy="cardCreditLimit", fetch=FetchType.EAGER)
    private Card card;

    public CardCreditLimit(){}

    public CardCreditLimit( CardColor cardColor, double cardLimit,double availableBalance) {

        this.cardColor = cardColor;
        this.cardLimit = cardLimit;
        this.availableBalance = availableBalance;

    }
    public CardCreditLimit(double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public long getId() {
        return id;
    }

    public double getCardLimit() {
        return cardLimit;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }
}

