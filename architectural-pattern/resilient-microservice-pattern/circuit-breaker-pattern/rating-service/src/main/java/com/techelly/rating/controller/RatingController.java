package com.techelly.rating.controller;

import com.techelly.rating.service.RatingService;
import com.techelly.dto.ProductRatingDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("{prodId}")
    public ResponseEntity<ProductRatingDto> getRating(@PathVariable int prodId) throws InterruptedException {
        var productRatingDto = this.ratingService.getRatingForProduct(prodId);
        return this.failRandomly(productRatingDto);
    }

    private ResponseEntity<ProductRatingDto> failRandomly(ProductRatingDto productRatingDto) throws InterruptedException {
        // simulate delay
        Thread.sleep(100);
        // simulate failure
        var random = ThreadLocalRandom.current().nextInt(1, 4);
        if(random < 3)
            return ResponseEntity.status(500).build();
        return ResponseEntity.ok(productRatingDto);
    }
    
    @GetMapping("/heartbeat")
    public String alive() {
    	return "I am alive";
    }

}
