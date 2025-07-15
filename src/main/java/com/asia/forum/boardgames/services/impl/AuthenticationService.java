package com.asia.forum.boardgames.services.impl;

import com.asia.forum.boardgames.dao.IUserDAO;
import com.asia.forum.boardgames.exceptions.LoginTakenException;
import com.asia.forum.boardgames.model.User;
import com.asia.forum.boardgames.services.IAuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final IUserDAO userDAO;

    @Autowired
    private HttpSession session;

    @Override
    public void register(User user) {
        if (this.userDAO.getUserByLogin(user.getLogin()).isPresent()) {
            throw new LoginTakenException("Ten login jest już zajęty: " + user.getLogin());
        }

        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setRole(User.Role.USER);
        this.userDAO.persistUser(user);
    }

    @Override
    public void registerAndLogin(User user) {
        register(user);
        // Po zarejestrowaniu użytkownika, pobieramy jego pełne dane z bazy (z ID)
        Optional<User> registeredUser = this.userDAO.getUserByLogin(user.getLogin());
        registeredUser.ifPresent(u -> session.setAttribute("user", u));
    }

    @Override
    public void authenticate(String login, String password) {

        Optional<User> userBox = this.userDAO.getUserByLogin(login);
        if (userBox.isPresent() && DigestUtils.md5DigestAsHex(password.getBytes())
                .equals(userBox.get().getPassword())) {
            session.setAttribute("user", userBox.get());
        }
    }

    @Override
    public void logout() {
        session.removeAttribute("user");
    }
}
