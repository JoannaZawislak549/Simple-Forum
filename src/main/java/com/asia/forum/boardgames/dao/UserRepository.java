package com.asia.forum.boardgames.dao;

import com.asia.forum.boardgames.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        this.users.add(new User(4, "janusz", "janusz1@testmail.com",
                "1e6f2ac43951a6721d3d26a379cc7f8b", User.Role.USER));
    }

    @Override
    public void persistUser(User user) {
        user.setId(++lastId);
        users.add(user);

    }

    @Override
    public User getUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }
}
