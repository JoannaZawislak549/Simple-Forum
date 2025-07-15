package com.asia.forum.boardgames.services;
import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import com.asia.forum.boardgames.model.view.ViewPost;
import com.asia.forum.boardgames.model.view.ViewTopic;
import java.util.ArrayList;
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
    List<ViewTopic> getTopicsByAuthor(String author);
    List<ViewPost> getPostsByAuthor(String author);

    /*Additional methods to convert model objects to view objects.
    Default mean that all classes implementing this interface
    will have these methods available.
     */
    default List<ViewTopic> convertToViewTopics(List<Topic> topics) {
        List<ViewTopic> viewTopics = new ArrayList<>();
        for (Topic topic : topics) {
            viewTopics.add(new ViewTopic(topic));
        }
        return viewTopics;
    }

    default List<ViewPost> convertToViewPosts(List<Post> posts) {
        List<ViewPost> viewPosts = new ArrayList<>();
        if (posts != null) {
            for (Post post : posts) {
                viewPosts.add(new ViewPost(post));
            }
        }
        return viewPosts;
    }
}
