package com.hackerzhenya.datagrip.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execute(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        stmt.close();
    }

    public <T> Collection<T> query(String query, SQLMapper<T> mapper) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);

        ResultSet result = stmt.getResultSet();
        List<T> rows = new ArrayList<>();

        while (result.next()) {
            rows.add(mapper.map(result));
        }

        result.close();
        stmt.close();

        return rows;
    }

    @FunctionalInterface
    public interface SQLMapper<T> {
        T map(ResultSet result) throws SQLException;
    }
}
