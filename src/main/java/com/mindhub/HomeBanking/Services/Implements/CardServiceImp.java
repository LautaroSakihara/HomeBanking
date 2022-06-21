package com.mindhub.HomeBanking.Services.Implements;

import com.mindhub.HomeBanking.Repositories.CardRepository;
import com.mindhub.HomeBanking.Services.CardService;
import com.mindhub.HomeBanking.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImp implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public void saveCard(Card card){
        cardRepository.save(card);
    }



}
