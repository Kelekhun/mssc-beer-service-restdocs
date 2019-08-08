package com.kapeed.msscbeerservice.web.controller;

import com.kapeed.msscbeerservice.repositories.BeerRepository;
import com.kapeed.msscbeerservice.web.mappers.BeerMapper;
import com.kapeed.msscbeerservice.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;


    @RequestMapping(method = RequestMethod.GET, path = "/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId") UUID beerId){


        return new ResponseEntity<>(
                 beerMapper
                         .BeerToBeerDTO(beerRepository
                                 .findById(beerId).get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDTO beerDTO){

        beerRepository.save(beerMapper.BeerDTOToBeer(beerDTO));

        return new ResponseEntity(HttpStatus.CREATED);

    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDTO beerDTO){

        beerRepository.findById(beerId).ifPresent(beer -> {
            beer.setBeerName(beerDTO.getBeerName());
            beer.setBeerStyle(beerDTO.getBeerStyle().name());
            beer.setPrice(beerDTO.getPrice());
            beer.setUpc(beerDTO.getUpc());

            beerRepository.save(beer);
        });


        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
