package com.techelly.cacheaside.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.techelly.cacheaside.entity.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
}
