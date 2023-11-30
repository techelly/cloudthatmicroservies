package com.techelly.cqrspattern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techelly.cqrspattern.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
