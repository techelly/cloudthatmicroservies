package com.techelly.springsecurityexampleusingtoyandbooklibapp.dao;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techelly.springsecurityexampleusingtoyandbooklibapp.entities.ERole;
import com.techelly.springsecurityexampleusingtoyandbooklibapp.entities.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}