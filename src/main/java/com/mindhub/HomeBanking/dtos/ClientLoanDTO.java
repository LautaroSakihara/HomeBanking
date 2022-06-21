package com.mindhub.HomeBanking.dtos;

import com.mindhub.HomeBanking.models.ClientLoan;

public class ClientLoanDTO {

    private long clientLoanid;

    private Long loanID;
    private String name;
    private Double amount;
    private int payments;

    public ClientLoanDTO(){}

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.clientLoanid = clientLoan.getId();

        this.loanID = clientLoan.getLoan().getId();

        this.name = clientLoan.getLoan().getName();

        this.amount = clientLoan.getAmount();

        this.payments = clientLoan.getPayments();

    }

    public Long getClientLoanid() {
        return clientLoanid;
    }

    public Long getLoanID() {
        return loanID;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
