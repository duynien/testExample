package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from users where username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);
    @Query(value = "select * from users where email = :email and username = :username", nativeQuery = true)
    User findUniqueByEmailAndUsername(@Param("email") String email,@Param("username") String username);
    @Query(value = "select * from users where email = :email", nativeQuery = true)
    User findUniqueByEmail(@Param("email") String email);
    @Query(value = "select * from users where username = :username", nativeQuery = true)
    User findUniqueByUsername(@Param("username") String username);
}
