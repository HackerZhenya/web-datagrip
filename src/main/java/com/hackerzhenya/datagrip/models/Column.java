package com.hackerzhenya.datagrip.models;

public class Column {
    private String name;
    private String type;
    private boolean nullable;
    private int position;

    public Column(String name, String type, boolean nullable, int position) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
        this.position = position;
    }

    // region Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    // endregion
}
