package com.hackerzhenya.datagrip.requests;

import com.hackerzhenya.datagrip.models.Connection;
import com.hackerzhenya.datagrip.repositories.UserRepository;
import com.hackerzhenya.datagrip.services.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpRequest {
    @Size(min = 4, message = "Логин не должен быть короче 4-х символов")
    @NotBlank(message = "Логин не может быть пустым")
    private String username;

    @Size(min = 6, message = "Пароль не должен быть короче 6-ти символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @NotBlank(message = "Необходимо подтвердить пароль")
    private String confirmPassword;

    @AssertTrue(message = "Пароли должны совпадать")
    boolean isPasswordConfirmed() {
        if (password == null || confirmPassword == null ||
                password.equals(Strings.EMPTY) || confirmPassword.equals(Strings.EMPTY)) {
            return true;
        }

        return password.equals(confirmPassword);
    }

    // region Getters & Setters

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // endregion
}
