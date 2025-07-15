package com.asia.forum.boardgames.services.impl;

import com.asia.forum.boardgames.dao.IUserDAO;
import com.asia.forum.boardgames.model.User;
import com.asia.forum.boardgames.services.IUserService;
import com.asia.forum.boardgames.exceptions.LoginTakenException;
import com.asia.forum.boardgames.validators.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserDAO userDAO;

    @Override
    public void updateLogin(User user, String newLogin) throws LoginTakenException {
        UserValidator.validateLogin(newLogin);
        user.setLogin(newLogin);
        this.userDAO.updateUser(user);
    }

    @Override
    public void updateEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        this.userDAO.updateUser(user);
    }

    @Override
    public void updatePassword(User user, String newPassword, String confirmPassword) {
        UserValidator.checkIfPasswordsMatch(newPassword, confirmPassword);
        String hashedPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        user.setPassword(hashedPassword);
        this.userDAO.updateUser(user);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String hashedPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        user.setPassword(hashedPassword);
        this.userDAO.updateUser(user);
    }
}
