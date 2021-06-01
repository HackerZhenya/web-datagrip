package com.hackerzhenya.datagrip.repositories;

import com.hackerzhenya.datagrip.models.Connection;
import com.hackerzhenya.datagrip.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
    Collection<Connection> findAllByUser(User user);
    Connection getById(UUID id);
}
