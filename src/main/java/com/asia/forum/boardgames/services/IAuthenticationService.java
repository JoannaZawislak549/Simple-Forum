package com.asia.forum.boardgames.services;
import com.asia.forum.boardgames.model.User;

public interface IAuthenticationService {
    void register(User user);
    void authenticate(String login, String password);
    void logout();
}
