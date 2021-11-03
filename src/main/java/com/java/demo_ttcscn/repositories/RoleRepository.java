package com.java.demo_ttcscn.repositories;

import com.java.demo_ttcscn.enitities.enumValue.ERole;
import com.java.demo_ttcscn.enitities.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query(value = "select * from roles where name = :name", nativeQuery = true)
    Role findByName(@Param("name") ERole name);
}
