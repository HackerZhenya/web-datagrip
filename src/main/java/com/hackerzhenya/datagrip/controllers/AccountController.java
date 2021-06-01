package com.hackerzhenya.datagrip.controllers;

import com.hackerzhenya.datagrip.requests.SignUpRequest;
import com.hackerzhenya.datagrip.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("sign-in")
    public String getSignIn() {
        return "account/sign-in";
    }

    @GetMapping("sign-up")
    public String getSignUp(@ModelAttribute("signUp") SignUpRequest request) {
        return "account/sign-up";
    }

    @PostMapping("sign-up")
    public String postSignUp(@Valid @ModelAttribute("signUp") SignUpRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "account/sign-up";
        }

        if (userService.userExists(request.getUsername())) {
            result.addError(new ObjectError("username",
                    "Пользователь с таким именем уже зарегистрирован"));
            return "account/sign-up";
        }

        userService.register(request);
        return "redirect:/";
    }

    @GetMapping("/sign-out")
    public String getSignOut(HttpServletRequest request, HttpServletResponse response) {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }

    @GetMapping
    public String getIndex() {
        return "redirect:/connections";
    }
}
