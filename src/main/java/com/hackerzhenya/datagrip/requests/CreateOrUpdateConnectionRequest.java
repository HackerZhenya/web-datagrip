package com.hackerzhenya.datagrip.requests;

import com.hackerzhenya.datagrip.models.Connection;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class CreateOrUpdateConnectionRequest {
    private UUID id;

    @NotBlank(message = "Хост не должен быть пустым")
    private String host;

    @Min(value = 0, message = "Порт не может быть отрицательным числом")
    @Max(value = 65_535, message = "Порт не может больше чем 65'535")
    private int port;

    @NotBlank(message = "Имя пользователя не должно быть пустым")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    @NotBlank(message = "База данных должна быть указана")
    private String database;

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

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    // endregion

    public void mapTo(Connection connection) {
        connection.setHost(getHost());
        connection.setPort(getPort());
        connection.setUsername(getUsername());
        connection.setPassword(getPassword());
        connection.setDatabase(getDatabase());
    }

    public void mapFrom(Connection connection) {
        setId(connection.getId());
        setHost(connection.getHost());
        setPort(connection.getPort());
        setUsername(connection.getUsername());
        setPassword(connection.getPassword());
        setDatabase(connection.getDatabase());
    }
}
