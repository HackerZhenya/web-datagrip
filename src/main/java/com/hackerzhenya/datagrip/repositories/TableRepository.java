package com.hackerzhenya.datagrip.repositories;

import com.hackerzhenya.datagrip.models.Column;
import com.hackerzhenya.datagrip.models.Table;
import com.hackerzhenya.datagrip.utils.Executor;
import org.springframework.data.util.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class TableRepository {
    private final Executor executor;

    public TableRepository(Connection connection) {
        this.executor = new Executor(connection);
    }

    public TableRepository(com.hackerzhenya.datagrip.models.Connection connection) throws SQLException {
        this(DriverManager.getConnection(
                connection.getConnectionString(),
                connection.getUsername(),
                connection.getPassword()
        ));
    }

    public Collection<Table> getTables() {
        try {
            var tables = executor.query(
                    "select * from information_schema.tables where table_schema != 'information_schema' and table_schema not like 'pg_%'",
                    result -> new Table(
                            result.getString("table_schema"),
                            result.getString("table_name")));

            for (var table : tables) {
                table.setColumns(
                        getColumns(table.getSchema(), table.getName()));
            }

            return tables;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Table getTable(String table) {
        if (table.contains(".")) {
            var pieces = table.split("\\.", 2);
            return getTable(pieces[0], pieces[1]);
        }

        return getTable("public", table);
    }

    public Table getTable(String schema, String tableName) {
        try {
            var tables = executor.query(
                    String.format("select * from information_schema.tables where table_schema = '%s' and table_name = '%s'",
                            schema, tableName),
                    result -> new Table(
                            result.getString("table_schema"),
                            result.getString("table_name")
                    ));

            if (tables.size() == 0) {
                return null;
            }

            if (tables.size() > 1) {
                throw new RuntimeException("Если ты видишь эту надпись, значит произошло что-то страшное");
            }

            var table = tables.stream().findFirst().orElseThrow();
            table.setColumns(getColumns(schema, tableName));

            return table;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private Collection<Column> getColumns(String schema, String table) {
        try {
            return executor.query(
                    String.format("select * from information_schema.columns where table_schema = '%s' and table_name = '%s'",
                            schema, table),
                    result -> new Column(
                            result.getString("column_name"),
                            result.getString("data_type"),
                            result.getBoolean("is_nullable"),
                            result.getInt("ordinal_position")));
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Pair<Table, Collection<Map<String, String>>> getRows(String tableName, int page, int limit) {
        var table = getTable(tableName);
        return Pair.of(table, getRows(table, page, limit));
    }

    public Collection<Map<String, String>> getRows(Table table, int page, int limit) {
        try {
            return executor.query(String.format("select * from \"%s\".\"%s\" limit %d offset %d",
                    table.getSchema(), table.getName(), limit, (page - 1) * limit),
                    result -> {
                        var dict = new HashMap<String, String>();

                        for (Column column : table.getColumns()) {
                            dict.put(column.getName(), Optional.ofNullable(result.getString(column.getName()))
                                                               .orElse("<null>"));
                        }

                        return dict;
                    });
        } catch (SQLException exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
