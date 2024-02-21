package com.example.demoparkapi.repositories;

import com.example.demoparkapi.entities.User;
import com.example.demoparkapi.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query(value = "SELECT u.role FROM User u WHERE u.username LIKE :username")
    Role findRoleByUsername(@Param("username") String username);
}
