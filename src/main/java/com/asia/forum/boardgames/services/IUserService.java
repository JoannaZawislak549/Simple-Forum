package com.asia.forum.boardgames.services;

import com.asia.forum.boardgames.model.User;
import com.asia.forum.boardgames.exceptions.LoginTakenException;
import com.asia.forum.boardgames.exceptions.UserValidationException;

public interface IUserService {
    void updateLogin(User user, String newLogin) throws LoginTakenException;
    void updateEmail(User user, String newEmail);
    void updatePassword(User user, String newPassword, String confirmPassword) throws UserValidationException;
}
