package com.techelly.cacheaside.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.techelly.cacheaside.dto.ProductDto;
import com.techelly.cacheaside.repository.ProductRepository;
import com.techelly.cacheaside.service.ProductService;
import com.techelly.cacheaside.util.EntityDtoUtil;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@ConditionalOnProperty(name = "cache.enabled", havingValue = "false")
public class ProductServiceWithNoCache implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Mono<ProductDto> getProduct(Integer id) {
        return this.productRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<Void> updateProduct(Integer id, Mono<ProductDto> mono) {
        return this.productRepository.findById(id)
                .zipWith(mono)
                .doOnNext(t -> t.getT1().setQtyAvailable(t.getT2().getQuantityAvailable()))
                .map(Tuple2::getT1)
                .flatMap(this.productRepository::save)
                .then();
    }

}
