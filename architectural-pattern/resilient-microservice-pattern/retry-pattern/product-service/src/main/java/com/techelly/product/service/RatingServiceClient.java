package com.techelly.product.service;

import com.techelly.dto.ProductRatingDto;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

@Service
@AllArgsConstructor
public class RatingServiceClient {

    private static final Logger log = LoggerFactory.getLogger(RatingServiceClient.class);
    private final RestClient client;
    private final ExecutorService executorService;

    @Retry(name = "ratingService", fallbackMethod = "onError")
    public CompletionStage<ProductRatingDto> getProductRatingDto(int productId) {
        return CompletableFuture.supplyAsync(() -> this.getRating(productId), executorService);
    }

    private ProductRatingDto getRating(int productId){
        return this.client.get()
                          .uri("{productId}", productId)
                          .retrieve()
                          .body(ProductRatingDto.class);
    }

    private CompletionStage<ProductRatingDto> onError(int productId, Throwable throwable) {
        log.error("error", throwable);
        return CompletableFuture.completedFuture(ProductRatingDto.of(0, Collections.emptyList()));
    }

}
