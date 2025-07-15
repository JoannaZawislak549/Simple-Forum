package com.asia.forum.boardgames.dao;

import com.asia.forum.boardgames.model.Topic;

import java.util.List;

public interface ITopicDAO {
    List<Topic> getAllTopics();
    Topic getTopicById(int id);
    void persistTopic(Topic topic);
    Topic createTopic(String title, String author);
    List<Topic> getTopicsByAuthor(String author);
    List<Topic> findTopicsByTitleContaining(String query);
}
