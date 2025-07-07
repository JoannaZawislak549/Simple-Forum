package com.asia.forum.boardgames.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private int id;
    private String login;
    private String email;
    private String password;
    private Role role;

    public enum Role {
        USER,
        ADMIN,
        MODERATOR
    }
}
