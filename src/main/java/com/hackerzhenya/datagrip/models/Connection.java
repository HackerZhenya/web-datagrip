package com.hackerzhenya.datagrip.models;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "connections")
public class Connection {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String host;

    @Column(nullable = false)
    private int port;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String database;

    @ManyToOne(optional = false)
    private User user;

    // region Getters & Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    // endregion

    public String getConnectionString() {
        return String.format("jdbc:postgresql://%s:%s/%s", host, port, database);
    }

    @Override
    public String toString() {
        return String.format("Connection{id=%s, host='%s', port=%d, username='%s', password='%s', database='%s'}",
                id, host, port, username, password, database);
    }
}
