package com.mindhub.HomeBanking.Controllers;

import com.mindhub.HomeBanking.Repositories.CreditCardLimitRepository;
import com.mindhub.HomeBanking.Services.CardService;
import com.mindhub.HomeBanking.Services.ClientService;
import com.mindhub.HomeBanking.Utils.CardUtils;
import com.mindhub.HomeBanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.HomeBanking.Utils.Utilities.getRandomNumber;

@RestController//definirá al controlador como un controlador delimitado por las restricciones de Rest.
@RequestMapping("/api")
public class CardsController {
    @Autowired
    private CreditCardLimitRepository creditCardLimitRepository;
    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;

    @PatchMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @RequestParam long id ) {
        Client client = clientService.getClientCurrent(authentication);
        Card cardActive = client.getCards().stream().filter(card -> card.getId() == id && card.isActive()).findFirst().orElse(null);

        cardActive.setActive(false);
        cardService.saveCard(cardActive);
        return new ResponseEntity<>("You have your card disabled", HttpStatus.CREATED);
    }
    @PostMapping(path = "/clients/current/cards")

    public ResponseEntity<Object> newCard(Authentication authentication, @RequestParam CardColor color,
                                          @RequestParam CardType type) {

        Client client = clientService.getClientCurrent(authentication);

        Set<Card> cards = client.getCards().stream().filter(card1 ->card1.getType() == type && card1.isActive()).collect(Collectors.toSet());
        Set<Card> cardsColor = cards.stream().filter(card1 ->card1.getColor() == color && card1.isActive()).collect(Collectors.toSet());
        CardCreditLimit cardCreditLimit = creditCardLimitRepository.findAll().stream().filter(limit -> limit.getCardColor() == color && type==CardType.CREDIT).findFirst().orElse(null);
        if(cardsColor.size() >= 1)
            return new ResponseEntity<>("You have already 1 card of this color", HttpStatus.FORBIDDEN);

        if(cards.size() >= 3)
            return new ResponseEntity<>("You have already 3 cards of this type", HttpStatus.FORBIDDEN);


        String randomNumber = CardUtils.getCardNumber(1000,9999);
        int cvv = CardUtils.getCVV(100,999);

        Card card = new Card(type,client.getFirstName()+ " " +client.getLastName(),color,randomNumber,cvv,
                LocalDateTime.now(),LocalDateTime.now().plusYears(5),client,cardCreditLimit);
        cardService.saveCard(card);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
