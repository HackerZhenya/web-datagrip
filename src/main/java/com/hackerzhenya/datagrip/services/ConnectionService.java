package com.hackerzhenya.datagrip.services;

import com.hackerzhenya.datagrip.models.Connection;
import com.hackerzhenya.datagrip.models.User;
import com.hackerzhenya.datagrip.repositories.ConnectionRepository;
import com.hackerzhenya.datagrip.requests.CreateOrUpdateConnectionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class ConnectionService {
    @Autowired
    private ConnectionRepository repository;

    private User getUser() {
        return (User) SecurityContextHolder.getContext()
                                           .getAuthentication()
                                           .getPrincipal();
    }

    public Collection<Connection> getConnections() {
        return repository.findAllByUser(getUser());
    }

    public Connection getConnection(UUID id) {
        return repository.getById(id);
    }

    public Connection createConnection(CreateOrUpdateConnectionRequest request) {
        var connection = new Connection();

        request.mapTo(connection);
        connection.setUser(getUser());

        repository.save(connection);

        return connection;
    }

    public Connection updateConnection(CreateOrUpdateConnectionRequest request) {
        var connection = repository.getById(request.getId());

        request.mapTo(connection);
        repository.save(connection);

        return connection;
    }

    public void deleteConnection(UUID id) {
        repository.deleteById(id);
    }
}
