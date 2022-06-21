package com.mindhub.HomeBanking.Controllers;


import com.mindhub.HomeBanking.Services.AccountService;
import com.mindhub.HomeBanking.Services.ClientService;
import com.mindhub.HomeBanking.dtos.AccountDTO;
import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.HomeBanking.Utils.Utilities.getRandomNumber;
//Con el @PathVariable podemos hacer que dentro de nuestro @RequestMapping tengamos una
//variable de ruta
@RestController//definirá al controlador como un controlador delimitado por las restricciones de Rest.
@RequestMapping("/api")
public class AccountController {
    @Autowired//Spring será capaz de llevar a cabo la inyección de dependencias sobre el atributo marcado

    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")

    public List<AccountDTO> getAccounts() {

        return accountService.getAccounts();

    }

    @GetMapping(path = "/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication) {
        Client client = clientService.getClientCurrent(authentication);
        return client.getAccounts().stream().filter(account -> account.isActive()).map(AccountDTO::new).collect(Collectors.toList());
    }
    @PatchMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam long id) {
        Client client = clientService.getClientCurrent(authentication);
        Account currentAccount = client.getAccounts().stream().filter(account -> account.getId() == id && account.isActive()).findFirst().orElse(null);

        if (currentAccount.getBalance() > 0){
            return new ResponseEntity<>("You need to transfer all the money to another account before disabled it", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(currentAccount)){
            return new ResponseEntity<>("Not your account", HttpStatus.FORBIDDEN);
        }

        currentAccount.setActive(false);
        accountService.saveAccount(currentAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        return accountService.getAccount(id);
    }

    @PostMapping(path = "/clients/current/accounts")

    public ResponseEntity<Object> newAccount(Authentication authentication) {

        Client client = clientService.getClientCurrent(authentication);

        if(client.getAccounts().size() >= 3) //agregar isActive
            return new ResponseEntity<>("You have already 3 accounts", HttpStatus.FORBIDDEN);

        Account account = new Account("VIN" + getRandomNumber(1,999999999),LocalDateTime.now(), 0,client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}


