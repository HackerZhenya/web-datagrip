package com.hackerzhenya.datagrip.services;

import com.hackerzhenya.datagrip.models.Connection;
import com.hackerzhenya.datagrip.models.Table;
import com.hackerzhenya.datagrip.repositories.TableRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TableService {
    @Autowired
    private ConnectionService service;
    private static final Map<String, TableRepository> repositories = new HashMap<>();

    public Collection<Table> getTables(UUID connectionId) {
        return getTables(service.getConnection(connectionId));
    }

    public Collection<Table> getTables(Connection connection) {
        return getRepository(connection).getTables();
    }

    public Pair<Table, Collection<Map<String, String>>> getRows(UUID connectionId, String table, int page, int limit) {
        return getRows(service.getConnection(connectionId), table, page, limit);
    }

    public Pair<Table, Collection<Map<String, String>>> getRows(Connection connection, String table,
                                                                int page, int limit) {
        return getRepository(connection).getRows(table, page, limit);
    }

    private @NotNull TableRepository getRepository(Connection connection) {
        var hash = connection.toString();

        if (!repositories.containsKey(hash)) {
            try {
                var repository = new TableRepository(connection);
                repositories.put(hash, repository);
                return repository;
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }

        return repositories.get(hash);
    }
}
