package com.asia.forum.boardgames.controllers;

import com.asia.forum.boardgames.exceptions.LoginTakenException;
import com.asia.forum.boardgames.exceptions.UserValidationException;
import com.asia.forum.boardgames.model.User;
import com.asia.forum.boardgames.services.IAuthenticationService;
import com.asia.forum.boardgames.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                         @RequestParam("password2") String password2) {

        try {
            UserValidator.validateUser(user);
            UserValidator.checkIfPasswordsMatch(user.getPassword(), password2);
            this.authenticationService.registerAndLogin(user);
        } catch (UserValidationException | LoginTakenException e) {
            return "redirect:/register";
        }

        return "redirect:/main";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login,
                       @RequestParam("password") String password) {
        try {
            UserValidator.validateLogin(login);
            UserValidator.validatePassword(password);
        } catch (UserValidationException e) {
            return "redirect:/login";
        }

        this.authenticationService.authenticate(login, password);
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/login";
    }

}
