package com.mindhub.HomeBanking.Services;

import com.mindhub.HomeBanking.dtos.ClientLoanDTO;
import com.mindhub.HomeBanking.dtos.LoanDTO;
import com.mindhub.HomeBanking.models.ClientLoan;
import com.mindhub.HomeBanking.models.Loan;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface LoanService {
    Loan getLoanId(long id);

    List<LoanDTO> getLoans();

    void saveClientLoan(ClientLoan clientLoan);

    ClientLoanDTO getClientLoansById(@PathVariable long id);

    List<ClientLoanDTO> getClientLoans();
}
