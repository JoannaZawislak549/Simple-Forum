package com.asia.forum.boardgames.dao.impl;

import com.asia.forum.boardgames.dao.IUserDAO;
import com.asia.forum.boardgames.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepository implements IUserDAO {
    private final List<User> users = new ArrayList<>();
    private int lastId = 3;

    public UserRepository() {
        this.users.add(new User(1, "admin", "admin@admin.com",
                "21232f297a57a5a743894a0e4a801fc3", User.Role.ADMIN));

        this.users.add(new User(2, "asia123", "asia@testmail.com",
                "ef9455e7352fc6711fd9452f30802349", User.Role.USER));
        this.users.add(new User(3, "PierwszyModerator", "moderator@testmail.com",
                "0408f3c997f309c03b08bf3a4bc7b730", User.Role.MODERATOR));
        this.users.add(new User(4, "janusz1", "janusz1@testmail.com",
                "1e6f2ac43951a6721d3d26a379cc7f8b", User.Role.USER));
    }

    @Override
    public void persistUser(User user) {
        user.setId(++lastId);
        users.add(user);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                break;
            }
        }
    }
}
