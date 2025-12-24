package com.ums.server.repository;

import com.ums.server.models.UmsUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UmsUsers, String> {

    Optional<UmsUsers> findByEmailIgnoreCase(String email);

}
