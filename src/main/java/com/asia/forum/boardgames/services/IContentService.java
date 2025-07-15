package com.asia.forum.boardgames.services;
import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import com.asia.forum.boardgames.model.view.ViewPost;
import com.asia.forum.boardgames.model.view.ViewTopic;
import java.util.HashMap;
import java.util.List;


public interface IContentService {
    List<ViewTopic> showSortedTopicsByLastPost();
    ViewTopic getTopicById(int id);
    List<ViewPost> getAllPostsForTopicId(int topicId);
    Topic createTopic(String title, String author);
    Post createReply(String content, int topicId, String author);
    HashMap<Integer, List<ViewPost>> getAllPostsForTopics(List<ViewTopic> topics);
    List<ViewTopic> searchTopicOrPost(String query);
    void updatePost(int topicId, int postId, String newContent);
    void deletePost(int topicId, int postId);
    Post getPost(int topicId, int postId);
}
