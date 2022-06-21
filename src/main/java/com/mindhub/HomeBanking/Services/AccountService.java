package com.mindhub.HomeBanking.Services;

import com.mindhub.HomeBanking.dtos.AccountDTO;
import com.mindhub.HomeBanking.models.Account;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
    void saveAccount(Account account);

    List<AccountDTO> getAccounts();

    AccountDTO getAccount(@PathVariable long id);

    Account getAccountByNumber(String number);
}
