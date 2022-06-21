package com.mindhub.HomeBanking.Controllers;

import com.mindhub.HomeBanking.Services.AccountService;
import com.mindhub.HomeBanking.Services.ClientService;
import com.mindhub.HomeBanking.Services.LoanService;
import com.mindhub.HomeBanking.Services.TransactionService;
import com.mindhub.HomeBanking.dtos.ClientLoanDTO;
import com.mindhub.HomeBanking.dtos.LoanApplicationDTO;
import com.mindhub.HomeBanking.dtos.LoanDTO;
import com.mindhub.HomeBanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.HomeBanking.models.TransactionType.CREDITO;

@RestController //restricciones de rest PUT POST GET PATH
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/clientLoans")
    public List<ClientLoanDTO> getClientLoans() {

        return loanService.getClientLoans();

    }
    @GetMapping ("clientLoans/{id}")
    public ClientLoanDTO getClientLoans(@PathVariable long id){
        return loanService.getClientLoansById(id);
    }

    @GetMapping("/loans")
    public List <LoanDTO> getLoans() {

        return loanService.getLoans();

    }

    @Transactional
    @PostMapping(path = "/loans")

    public ResponseEntity<Object> newLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {
        Client currentClient = clientService.getClientCurrent(authentication);
        Account account = accountService.getAccountByNumber(loanApplicationDTO.getAccountNumberDestiny());
        Loan loan = loanService.getLoanId(loanApplicationDTO.getLoanId());

        if (currentClient.getLoans().contains(loan)){
            return new ResponseEntity<>("You already had a loan of this type", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() <= 0) {
            return new ResponseEntity<>("The amount must be higher than 0", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getLoanId() <= 0) {
            return new ResponseEntity<>("The loan type must not be empty", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() <= 0) {
            return new ResponseEntity<>("The payments must be higher than 0", HttpStatus.FORBIDDEN);
        }
        if (loan == null) {
            return new ResponseEntity<>("The loan does not exist", HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("The amount exceed the loan max amount", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("The payments are not available for this type of loan", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);
        }
        if (!currentClient.getAccounts().contains(account)) {
            return new ResponseEntity<>("The account is not associated to the client", HttpStatus.FORBIDDEN);
        }


        ClientLoan clientLoan = new ClientLoan(loan,loanApplicationDTO.getAmount()+(loanApplicationDTO.getAmount()*0.20),loanApplicationDTO.getPayments(),currentClient);
        loanService.saveClientLoan(clientLoan);

        Transaction transaction =new Transaction(CREDITO, LocalDateTime.now(),"Approved Credit", loanApplicationDTO.getAmount(), account);
        transactionService.saveTransaction(transaction);

        account.setBalance(account.getBalance()+transaction.getAmount());

        return new ResponseEntity<>("Loan Created succesfully",HttpStatus.CREATED);

    }
}