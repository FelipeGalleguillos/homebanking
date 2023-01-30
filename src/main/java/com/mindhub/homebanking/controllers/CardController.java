package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;

    @DeleteMapping(path = "/cards/{id}")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @PathVariable Long id){

        cardService.deleteCard(clientService.getClientByEmail(authentication.getName()),cardService.getCardById(id));
        return new ResponseEntity<>("deleted",HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCard (
            Authentication authentication, @RequestParam String type,@RequestParam String color ){

        Client client = clientService.getClientByEmail(authentication.getName());

        if(client.getCards().stream().toArray().length >= 6){
            return new ResponseEntity<>("limit of cards reached", HttpStatus.FORBIDDEN);
        }
        if(type.equals("CREDIT") && client.getCards().stream().filter(card->card.getType().toString().equals(type)).toArray().length >= 3){
            return new ResponseEntity<>("can't create another credit card", HttpStatus.FORBIDDEN);
        }
        if(type.equals("DEBIT") && client.getCards().stream().filter(card->card.getType().toString().equals(type)).toArray().length >= 3){
            return new ResponseEntity<>("can't create another debit card", HttpStatus.FORBIDDEN);
        }
        if(client.getCards().stream().filter(card->card.getType().toString().equals(type) && card.getColor().toString().equals(color)).toArray().length > 0){
            return new ResponseEntity<>("card with color/type already created", HttpStatus.FORBIDDEN);
        }

        String cardNumber = CardUtils.getCardNumber();

        int cvv = CardUtils.getCardCvv();

        CardType cardType = null;
        CardColor cardColor = null;

        if(type.equals("CREDIT")){
            cardType = CardType.CREDIT;
        }else{
            cardType = CardType.DEBIT;
        }

        if(color.equals("GOLD")){
            cardColor = CardColor.GOLD;
        }
        if(color.equals("SILVER")){
            cardColor = CardColor.SILVER;
        }
        if(color.equals("TITANIUM")){
            cardColor = CardColor.TITANIUM;
        }

        Card card = new Card(client.getFirstName().concat(" "+client.getLastName()),cardType,cardColor,cardNumber,cvv,LocalDate.now());
        client.addCard(card);
        card.setClient(client);
        clientService.saveClient(client);
        cardService.saveCard(card);

        return new ResponseEntity<>("created", HttpStatus.CREATED);
    }
}
