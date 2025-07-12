package com.asia.forum.boardgames.services.impl;

import com.asia.forum.boardgames.dao.IPostDAO;
import com.asia.forum.boardgames.dao.ITopicDAO;
import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import com.asia.forum.boardgames.model.User;
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
import java.util.Map;

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

        // Convert to ViewTopic for presentation
        List<ViewTopic> viewTopics = new ArrayList<>();
        for (Topic topic : topics) {
            viewTopics.add(new ViewTopic(topic));
        }

        return viewTopics;
    }

    @Override
    public ViewTopic getTopicById(int id) {
        return new ViewTopic(this.topicDAO.getTopicById(id));
    }

    @Override
    public List<ViewPost> getAllPostsForTopicId(int topicId) {
        List<Post> posts = this.postDAO.getAllPostsForTopicId(topicId);
        List<ViewPost> viewPosts = new ArrayList<>();

        for(Post post : posts) {
            viewPosts.add(new ViewPost(post));
        }

        return viewPosts;
    }

    @Override
    public void createTopic(Topic topic, Post post, String title, String content) {
        topic.setTitle(title);
        User user = (User) session.getAttribute("user");
        if (user != null) {
            topic.setAuthor(user.getLogin());
        }
        topic.setDate(java.time.LocalDateTime.now());
        this.topicDAO.persistTopic(topic);
        post.setTopicId(topic.getId());
        post.setAuthor(topic.getAuthor());
        post.setDate(java.time.LocalDateTime.now());
        post.setContent(content);
        this.postDAO.persistPost(post);
    }

    @Override
    public void createReply(String content, int topicId) {
        Post post = new Post();
        post.setTopicId(topicId);
        User user = (User) session.getAttribute("user");
        String author = user.getLogin();
        post.setAuthor(author);
        post.setDate(java.time.LocalDateTime.now());
        post.setContent(content);
        this.postDAO.persistPost(post);
    }

    @Override
    public HashMap<Integer, List<ViewPost>> getAllPostsForTopics(List<ViewTopic> topics) {
        HashMap<Integer, List<ViewPost>> result = new HashMap<>();
        for (ViewTopic topic : topics) {
            List<Post> posts = this.postDAO.getAllPostsForTopicId(topic.getId());
            List<ViewPost> viewPosts = new ArrayList<>();
            for (Post post : posts) {
                viewPosts.add(new ViewPost(post));
            }
            result.put(topic.getId(), viewPosts);
        }
        return result;
    }

    @Override
    public List<ViewTopic> searchTopicOrPost(String query) {
        List<Topic> allTopics = this.topicDAO.getAllTopics();
        List<ViewTopic> matchedTopics = new ArrayList<>();

        for (Topic topic : allTopics) {
            boolean matches = false;
            if (topic.getTitle().toLowerCase().contains(query.toLowerCase())) {
                matches = true;
            } else {
                List<Post> posts = this.postDAO.getAllPostsForTopicId(topic.getId());
                if (posts != null) {
                    for (Post post : posts) {
                        if (post.getContent().toLowerCase().contains(query.toLowerCase())) {
                            matches = true;
                            break;
                        }
                    }
                }
            }
            if (matches) {
                matchedTopics.add(new ViewTopic(topic));
            }
        }
        return matchedTopics;
    }
}
