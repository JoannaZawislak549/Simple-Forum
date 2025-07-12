package com.asia.forum.boardgames.validators;

import com.asia.forum.boardgames.exceptions.UserValidationException;
import com.asia.forum.boardgames.model.User;

public class UserValidator {

    public static void validateLogin(String login) {
        if (login == null || !login.matches("\\w{3,20}$")) {
            throw new UserValidationException("Login musi być od 3 do 20 znaków i może zawierać tylko litery, cyfry i podkreślenia.");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || !password.matches("\\w{4,20}$")) {
            throw new UserValidationException("Hasło musi być od 4 do 20 znaków i może zawierać tylko litery, cyfry i podkreślenia.");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new UserValidationException("Email jest nieprawidłowy.");
        }
    }

    public static void checkIfPasswordsMatch(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new UserValidationException("Hasła nie pasują do siebie.");
        }
    }

    public static void validateUser(User user) {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
    }
}
