package com.asia.forum.boardgames.model.view;

import com.asia.forum.boardgames.model.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ViewPost {
    private int id;
    private int topicId;
    private String author;
    private String content;
    private String formattedDate;
    private LocalDateTime date;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");

    public ViewPost(Post post) {
        this.id = post.getId();
        this.topicId = post.getTopicId();
        this.author = post.getAuthor();
        this.content = post.getContent();
        this.date = post.getDate();
        this.formattedDate = post.getDate().format(formatter);
    }
}
