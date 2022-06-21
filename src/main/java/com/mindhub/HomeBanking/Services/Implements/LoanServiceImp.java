package com.mindhub.HomeBanking.Services.Implements;

import com.mindhub.HomeBanking.Repositories.ClientLoanRepository;
import com.mindhub.HomeBanking.Repositories.LoanRepository;
import com.mindhub.HomeBanking.Services.LoanService;
import com.mindhub.HomeBanking.dtos.ClientLoanDTO;
import com.mindhub.HomeBanking.dtos.LoanDTO;
import com.mindhub.HomeBanking.models.ClientLoan;
import com.mindhub.HomeBanking.models.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImp implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Override
    public Loan getLoanId(long id){
      return loanRepository.findById(id);
    }

    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }
    @Override
    public List<ClientLoanDTO> getClientLoans() {
        return clientLoanRepository.findAll().stream().map(ClientLoanDTO::new).collect(toList());
    }

    @Override
    public void saveClientLoan(ClientLoan clientLoan){
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public ClientLoanDTO getClientLoansById(@PathVariable long id){
        return clientLoanRepository.findById(id).map(ClientLoanDTO::new).orElse(null);
    }
}
