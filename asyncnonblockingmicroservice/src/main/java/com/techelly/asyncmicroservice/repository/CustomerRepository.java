package com.techelly.asyncmicroservice.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.techelly.asyncmicroservice.domain.Customer;
public interface CustomerRepository extends CrudRepository<Customer, String> {

//public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findByName(String name);
    List<Customer> findByRole(String role);

}
