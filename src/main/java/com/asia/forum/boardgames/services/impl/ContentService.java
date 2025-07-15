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
import java.util.Comparator;
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
    public Topic createTopic(String title, String author) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setAuthor(author);
        topic.setDate(LocalDateTime.now());
        this.topicDAO.persistTopic(topic);
        return topic;
    }

    @Override
    public Post createReply(String content, int topicId, String author) {
        Post post = new Post();
        post.setTopicId(topicId);
        post.setAuthor(author);
        post.setContent(content);
        post.setDate(LocalDateTime.now());
        this.postDAO.persistPost(post);
        return post;
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
        List<Topic> allTopics = this.topicDAO.getAllTopics();
        List<ViewTopic> userTopics = new ArrayList<>();

        for (Topic topic : allTopics) {
            if (author.equals(topic.getAuthor())) {
                userTopics.add(new ViewTopic(topic));
            }
        }

        // Sortowanie od najnowszych do najstarszych
        for (int i = 0; i < userTopics.size() - 1; i++) {
            for (int j = 0; j < userTopics.size() - i - 1; j++) {
                if (userTopics.get(j).getDate().isBefore(userTopics.get(j + 1).getDate())) {
                    ViewTopic temp = userTopics.get(j);
                    userTopics.set(j, userTopics.get(j + 1));
                    userTopics.set(j + 1, temp);
                }
            }
        }
        return userTopics;
    }

    @Override
    public List<ViewPost> getPostsByAuthor(String author) {
        List<ViewPost> userPosts = new ArrayList<>();
        List<Topic> allTopics = this.topicDAO.getAllTopics();

        for (Topic topic : allTopics) {
            List<Post> topicPosts = this.postDAO.getAllPostsForTopicId(topic.getId());
            if (topicPosts != null) {
                for (Post post : topicPosts) {
                    if (author.equals(post.getAuthor())) {
                        userPosts.add(new ViewPost(post));
                    }
                }
            }
        }

        // Sortowanie od najnowszych do najstarszych
        for (int i = 0; i < userPosts.size() - 1; i++) {
            for (int j = 0; j < userPosts.size() - i - 1; j++) {
                if (userPosts.get(j).getDate().isBefore(userPosts.get(j + 1).getDate())) {
                    ViewPost temp = userPosts.get(j);
                    userPosts.set(j, userPosts.get(j + 1));
                    userPosts.set(j + 1, temp);
                }
            }
        }
        return userPosts;
    }
}
