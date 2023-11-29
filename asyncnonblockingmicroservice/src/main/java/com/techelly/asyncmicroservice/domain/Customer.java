package com.techelly.asyncmicroservice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
//import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Customer {

    @Id
    private String id;

    private String name;

    private String role;

    private int age;

}
