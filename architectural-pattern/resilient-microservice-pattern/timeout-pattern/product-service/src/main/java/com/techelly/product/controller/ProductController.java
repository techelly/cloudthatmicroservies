package com.techelly.product.controller;

import com.techelly.dto.ProductDto;
import com.techelly.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("{productId}")
    public CompletableFuture<ProductDto> getProduct(@PathVariable int productId){
        return this.productService.getProductDto(productId);
    }

}
