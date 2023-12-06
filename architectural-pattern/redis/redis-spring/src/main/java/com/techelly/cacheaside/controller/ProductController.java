package com.techelly.cacheaside.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.techelly.cacheaside.dto.ProductDto;
import com.techelly.cacheaside.service.ProductService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public Mono<ProductDto> getProduct(@PathVariable Integer id){
        return this.productService.getProduct(id);
    }

    @PatchMapping("{id}")
    public Mono<Void> updateProduct(@PathVariable Integer id, @RequestBody Mono<ProductDto> mono){
        return this.productService.updateProduct(id, mono);
    }

}
