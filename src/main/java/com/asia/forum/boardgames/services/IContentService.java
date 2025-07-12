package com.asia.forum.boardgames.services;

import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import com.asia.forum.boardgames.model.view.ViewPost;
import com.asia.forum.boardgames.model.view.ViewTopic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IContentService {
    List<ViewTopic> showSortedTopicsByLastPost();
    ViewTopic getTopicById(int id);
    List<ViewPost> getAllPostsForTopicId(int topicId);
    void createTopic(Topic topic, Post post, String title, String content);
    void createReply(String content, int topicId);
    HashMap<Integer, List<ViewPost>> getAllPostsForTopics(List<ViewTopic> topics);
    List<ViewTopic> searchTopicOrPost(String query);
}
