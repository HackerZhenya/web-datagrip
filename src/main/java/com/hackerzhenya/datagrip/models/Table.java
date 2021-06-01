package com.hackerzhenya.datagrip.models;

import java.util.Collection;

public class Table {
    private String schema;
    private String name;
    private Collection<Column> columns;

    public Table(String schema, String name) {
        this.schema = schema;
        this.name = name;
    }

    public Table(String schema, String name, Collection<Column> columns) {
        this.schema = schema;
        this.name = name;
        this.columns = columns;
    }

    // region Getters & Setters

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Column> getColumns() {
        return columns;
    }

    public void setColumns(Collection<Column> columns) {
        this.columns = columns;
    }

    // endregion
}
