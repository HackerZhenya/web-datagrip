package com.hackerzhenya.datagrip.repositories;

import com.hackerzhenya.datagrip.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
