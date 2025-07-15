package com.asia.forum.boardgames.services.impl;

import com.asia.forum.boardgames.dao.IPostDAO;
import com.asia.forum.boardgames.dao.ITopicDAO;
import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import com.asia.forum.boardgames.model.view.ViewPost;
import com.asia.forum.boardgames.model.view.ViewTopic;
import com.asia.forum.boardgames.services.IContentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService implements IContentService {
    private final ITopicDAO topicDAO;
    private final IPostDAO postDAO;
    @Autowired
    private HttpSession session;

    @Override
    public List<ViewTopic> showSortedTopicsByLastPost() {
        List<Topic> topics = this.topicDAO.getAllTopics();

        // Sort topics by the date of the latest post in each topic
        topics.sort((topic1, topic2) -> {
            LocalDateTime date1 = this.postDAO.getLatestPostForTopicId(topic1.getId()).getDate();
            LocalDateTime date2 = this.postDAO.getLatestPostForTopicId(topic2.getId()).getDate();

            // If post date is null (no post in topic), use the topic's creation date
            if(date1 == null) {
                date1 = topic1.getDate();
            }
            if(date2 == null) {
                date2 = topic2.getDate();
            }

            return date2.compareTo(date1);
        });

        return convertToViewTopics(topics);
    }

    @Override
    public ViewTopic getTopicById(int id) {
        return new ViewTopic(this.topicDAO.getTopicById(id));
    }

    @Override
    public List<ViewPost> getAllPostsForTopicId(int topicId) {
        return convertToViewPosts(this.postDAO.getAllPostsForTopicId(topicId));
    }

    @Override
    public Topic createTopic(String title, String author) {
        return this.topicDAO.createTopic(title, author);
    }

    @Override
    public Post createReply(String content, int topicId, String author) {
        return this.postDAO.createPost(content, topicId, author);
    }

    @Override
    public HashMap<Integer, List<ViewPost>> getAllPostsForTopics(List<ViewTopic> topics) {
        HashMap<Integer, List<ViewPost>> result = new HashMap<>();
        for (ViewTopic topic : topics) {
            result.put(topic.getId(), convertToViewPosts(this.postDAO.getAllPostsForTopicId(topic.getId())));
        }
        return result;
    }

    @Override
    public List<ViewTopic> searchTopicOrPost(String query) {
        List<Topic> matchedTopics = new ArrayList<>();

        // Search in topic titles
        matchedTopics.addAll(this.topicDAO.findTopicsByTitleContaining(query));

        // Search in post content
        for (Topic topic : this.topicDAO.getAllTopics()) {
            if (!matchedTopics.contains(topic) && this.postDAO.hasPostsContaining(topic.getId(), query)) {
                matchedTopics.add(topic);
            }
        }

        return convertToViewTopics(matchedTopics);
    }

    @Override
    public void updatePost(int topicId, int postId, String newContent) {
        this.postDAO.updatePost(topicId, postId, newContent);
    }

    @Override
    public void deletePost(int topicId, int postId) {
        this.postDAO.deletePost(topicId, postId);
    }

    @Override
    public Post getPost(int topicId, int postId) {
        return this.postDAO.getPost(topicId, postId);
    }

    @Override
    public List<ViewTopic> getTopicsByAuthor(String author) {
        return convertToViewTopics(this.topicDAO.getTopicsByAuthor(author));
    }

    @Override
    public List<ViewPost> getPostsByAuthor(String author) {
        return convertToViewPosts(this.postDAO.getPostsByAuthor(author));
    }
}
