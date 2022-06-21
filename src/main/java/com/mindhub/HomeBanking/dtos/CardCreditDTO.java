package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.CardCreditLimit;

public class CardCreditDTO {
    private double cardLimit,availableBalance;

    public CardCreditDTO(){};

    public CardCreditDTO(CardCreditLimit cardCreditLimit){
        this.cardLimit = cardCreditLimit.getCardLimit();
        this.availableBalance = cardCreditLimit.getAvailableBalance();
    }

    public double getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }
}
