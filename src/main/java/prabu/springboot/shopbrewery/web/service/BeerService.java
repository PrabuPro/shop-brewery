package prabu.springboot.shopbrewery.web.service;

import prabu.springboot.shopbrewery.web.model.BeerDTO;

import java.util.UUID;

public interface BeerService {
    BeerDTO getBeerById(UUID beerId);

    BeerDTO saveBeer(BeerDTO beerDTO);

    void updateBeer(UUID beerId, BeerDTO beerDTO);

    void deleteBeerById(UUID beerId);
}
