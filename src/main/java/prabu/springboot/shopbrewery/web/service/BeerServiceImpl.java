package prabu.springboot.shopbrewery.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prabu.springboot.shopbrewery.web.model.BeerDTO;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDTO getBeerById(UUID beerId) {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("beer name")
                .beerStyle("beer style")
                .build();
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDTO beerDTO) {

    }

    @Override
    public void deleteBeerById(UUID beerId) {
      log.debug("delete beer: " + beerId );
    }
}
