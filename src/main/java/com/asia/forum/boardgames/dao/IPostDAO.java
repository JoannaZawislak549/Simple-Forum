package com.asia.forum.boardgames.dao;

import com.asia.forum.boardgames.model.Post;

import java.util.List;

public interface IPostDAO {
    List<Post> getAllPostsForTopicId(int topicId);
    void persistPost(Post post);
    Post getLatestPostForTopicId(int topicId);
    void updatePost(int topicId, int postId, String newContent);
    void deletePost(int topicId, int postId);
    Post getPost(int topicId, int postId);
    Post createPost(String content, int topicId, String author);
    List<Post> getPostsByAuthor(String author);
    boolean hasPostsContaining(int topicId, String query);
}
