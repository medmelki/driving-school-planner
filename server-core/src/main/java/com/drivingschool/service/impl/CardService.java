package com.drivingschool.service.impl;


import com.drivingschool.model.Card;
import com.drivingschool.service.ICardService;
import org.springframework.stereotype.Repository;

@Repository
public class CardService extends GenericService<Card, String> implements ICardService {

    public CardService() {
        super(Card.class);
    }
}
