package com.mindhub.HomeBanking.Services.Implements;

import com.mindhub.HomeBanking.Repositories.TransactionRepository;
import com.mindhub.HomeBanking.Services.TransactionService;
import com.mindhub.HomeBanking.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImp implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction (Transaction transaction){
        transactionRepository.save(transaction);
    }

}
