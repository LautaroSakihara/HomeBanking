package com.mindhub.HomeBanking.Controllers;

import com.mindhub.HomeBanking.Services.AccountService;
import com.mindhub.HomeBanking.Services.ClientService;
import com.mindhub.HomeBanking.dtos.ClientDTO;
import com.mindhub.HomeBanking.models.Account;
import com.mindhub.HomeBanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.HomeBanking.Utils.Utilities.getRandomNumber;

@RestController
@RequestMapping("/api")

public class ClientController {

        @Autowired
        private ClientService clientService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private AccountService accountService;

        @GetMapping("/clients")
        public List<ClientDTO> getClients() {

                return clientService.getClientsDTO();

        }

        @GetMapping("clients/{id}")
        public ClientDTO getClient(@PathVariable long id) {
                return clientService.getClientDTO(id);
        }

        @PostMapping("/clients")

        public ResponseEntity<Object> register(

                @RequestParam String firstName, @RequestParam String lastName,

                @RequestParam String email, @RequestParam String password) {


                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

                        return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

                }


                if (clientService.getClientByEmail(email) != null) {

                        return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);

                }

                //Guardo en una variable el nuevo cliente
                Client clientRegistered = new Client(firstName, lastName, email, passwordEncoder.encode(password));
                clientService.saveClient(clientRegistered);

                Account account = new Account("VIN" + getRandomNumber(1,999999999), LocalDateTime.now(),0,clientRegistered);
                accountService.saveAccount(account);

                return new ResponseEntity<>(HttpStatus.CREATED);


        }
        @GetMapping("/clients/current")

        public ClientDTO getClient(Authentication authentication) {

               // return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
                //Client client = clientRepository.findByEmail(authentication.getName());
                //return new ClientDTO(client);
                Client clientNew = clientService.getClientCurrent(authentication);
                return new ClientDTO(clientNew);
        }
}




