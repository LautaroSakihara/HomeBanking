package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.CardType;

public class PaymentDTO {
    private CardType type;
    private String cardHolder,cardNumber,accountNumber,description,thruDate;
    private int cvv;
    private double amount;
    public PaymentDTO(){}

    public PaymentDTO(CardType type, String cardHolder, String cardNumber, String accountNumber, String description, String thruDate, int cvv, double amount) {
        this.type = type;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.accountNumber = accountNumber;
        this.description = description;
        this.thruDate = thruDate;
        this.cvv = cvv;
        this.amount = amount;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getThruDate() {
        return thruDate;
    }

    public int getCvv() {
        return cvv;
    }
    public double getAmount() {
        return amount;
    }

}
