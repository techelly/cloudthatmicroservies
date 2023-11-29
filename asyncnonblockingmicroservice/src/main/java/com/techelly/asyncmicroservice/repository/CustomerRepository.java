package com.techelly.asyncmicroservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.techelly.asyncmicroservice.domain.Customer;

import java.util.List;
//public interface CustomerRepository extends CrudRepository<Customer, String> {

public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findByName(String name);
    List<Customer> findByRole(String role);

}
