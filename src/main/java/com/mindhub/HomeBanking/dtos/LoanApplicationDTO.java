package com.mindhub.HomeBanking.dtos;

public class LoanApplicationDTO {
    private long LoanId;
    private double amount;
    private int payments;
    private String accountNumberDestiny;

    public LoanApplicationDTO(long loanId, double amount, int payments, String accountNumberDestiny) {

        this.LoanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.accountNumberDestiny = accountNumberDestiny;
    }

    public long getLoanId() {
        return LoanId;
    }

    public void setLoanName(long loanId) {
        LoanId= loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getAccountNumberDestiny() {
        return accountNumberDestiny;
    }

    public void setAccountNumberDestiny(String accountNumberDestiny) {
        this.accountNumberDestiny = accountNumberDestiny;
    }
}
