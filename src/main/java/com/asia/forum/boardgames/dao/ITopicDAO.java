package com.asia.forum.boardgames.dao;

import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;

import java.util.List;

public interface ITopicDAO {

    public List<Topic> getAllTopics();
    public Topic getTopicById(int id);
    public void persistTopic(Topic topic);
}
