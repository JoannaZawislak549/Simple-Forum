package com.asia.forum.boardgames.model.view;

import com.asia.forum.boardgames.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewTopic {
    private int id;
    private String title;
    private String author;
    private String formattedDate;
    private LocalDateTime date;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");

    public ViewTopic(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.author = topic.getAuthor();
        this.date = topic.getDate();
        this.formattedDate = topic.getDate().format(formatter);
    }

}

