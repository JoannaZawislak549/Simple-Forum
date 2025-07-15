package com.asia.forum.boardgames.dao;

import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;

import java.util.HashMap;
import java.util.List;

public interface IPostDAO {
    public void addPost(int topicId, Post post);
    public Post getPostById(int id);
    public List<Post> getAllPostsForTopicId(int topicId);
    public void persistPost(Post post);
    public Post getLatestPostForTopicId(int topicId);
    public String getLastPostAuthor(int topicId);
    HashMap<Integer, List<Post>> getAllPostsForTopics(List<Topic> topics);
    void updatePost(int topicId, int postId, String newContent);
    void deletePost(int topicId, int postId);
    Post getPost(int topicId, int postId);
}
