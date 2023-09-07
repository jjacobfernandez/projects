package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import com.techelevator.services.RestCatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api")
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }


    @RequestMapping(path = "/cards", method = RequestMethod.GET)
    public List<CatCard> catCardList() {
        return catCardDao.getCatCards();
    }
    @RequestMapping(path = "/cards/{id}", method = RequestMethod.GET)
    public CatCard catCard(@PathVariable int id) {
        CatCard catCard = catCardDao.getCatCardById(id);
        if(catCard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }else {
            return catCard;
        }
    }
@RequestMapping(path = "/cards/random", method = RequestMethod.GET)
    public CatCard randomCatCard() {

        CatCard catCard = new CatCard();

        catCard.setCatFact(catFactService.getFact().getText());

        catCard.setImgUrl(catPicService.getPic().getFile());


    return catCard;
}
@ResponseStatus(HttpStatus.CREATED)
@RequestMapping(path = "/cards", method = RequestMethod.POST)
    public CatCard addCatCard(@RequestBody CatCard catCard) {
        return catCardDao.createCatCard(catCard);
}

@RequestMapping(path = "/cards/{id}", method = RequestMethod.PUT)
    public CatCard updateCatCard(@RequestBody CatCard catCard, @PathVariable int id) {
        catCard.setCatCardId(id);
        try{
            CatCard updatedCatCard = catCardDao.updateCatCard(catCard);
            return updatedCatCard;
        }catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
    }
    @RequestMapping(path = "/cards/{id}", method = RequestMethod.DELETE)
    public void deleteCatCard(@PathVariable int id) {
        catCardDao.deleteCatCardById(id);
    }


}
