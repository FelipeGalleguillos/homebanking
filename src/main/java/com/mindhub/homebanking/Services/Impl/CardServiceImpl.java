package com.mindhub.homebanking.Services.Impl;

import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCard(Client client, Card card) {
        Set<Card> cards = client.getCards();
        cards.remove(card);
        client.setCards(cards);
        clientRepository.save(client);
        cardRepository.delete(card);
    }
}
