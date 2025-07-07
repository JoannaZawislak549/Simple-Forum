package com.asia.forum.boardgames.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Topic {
    private int id;
    private String title;
    private String author;
    private LocalDateTime date;
}
