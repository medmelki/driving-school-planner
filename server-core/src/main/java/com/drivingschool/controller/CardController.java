package com.drivingschool.controller;

import com.drivingschool.model.Card;
import com.drivingschool.service.ICardService;
import com.drivingschool.utils.PermissionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private ICardService cardService;

    @Autowired
    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @PreAuthorize(PermissionUtils.HAS_ANY_ROLE)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Card>> listAll() {


        List<Card> cards = cardService.findAll();
        if (cards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PreAuthorize(PermissionUtils.HAS_A_MANAGE_ROLE)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Void> addCard(@RequestBody Card card) {
        cardService.create(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize(PermissionUtils.HAS_A_MANAGE_ROLE)
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Card> updateCard(@RequestBody Card card) {
        cardService.update(card);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PreAuthorize(PermissionUtils.HAS_A_MANAGE_ROLE)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Card> deleteCard(@PathVariable String id) {

        Card card = cardService.read(id);
        cardService.delete(card);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
