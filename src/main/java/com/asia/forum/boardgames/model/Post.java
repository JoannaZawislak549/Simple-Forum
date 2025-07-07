package com.asia.forum.boardgames.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Post {
    private int id;
    private int topicId;
    private String author;
    private String content;
    private LocalDateTime date;

}

