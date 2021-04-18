package prabu.springboot.shopbrewery.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prabu.springboot.shopbrewery.web.model.BeerDTO;
import prabu.springboot.shopbrewery.web.service.BeerService;

import java.util.UUID;

@RequestMapping(BeerController.API_V_1_BEER)
@RestController
public class BeerController {

    public static final String API_V_1_BEER = "/api/v1/beer";
    private BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable("beerId")UUID beerId){
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveBeer(@RequestBody BeerDTO beerDTO){
        BeerDTO saveBeerDTO = beerService.saveBeer(beerDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", saveBeerDTO.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@PathVariable("beerId")UUID beerId, @RequestBody BeerDTO beerDTO){
        beerService.updateBeer(beerId, beerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId")UUID beerId){
        beerService.deleteBeerById(beerId);
    }

}
