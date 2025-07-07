package com.asia.forum.boardgames.dao;


import com.asia.forum.boardgames.model.User;

public interface IUserDAO {
    void persistUser(User user);
    User getUserByLogin(String login);

}
