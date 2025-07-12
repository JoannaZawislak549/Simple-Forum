package com.asia.forum.boardgames.dao;


import com.asia.forum.boardgames.model.User;

import java.util.Optional;

public interface IUserDAO {
    void persistUser(User user);
    Optional<User> getUserByLogin(String login);

}
