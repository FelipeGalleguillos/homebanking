package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

public interface CardService {

    void saveCard(Card card);

    Card getCardById(Long id);

    void deleteCard(Client client, Card card);
}
