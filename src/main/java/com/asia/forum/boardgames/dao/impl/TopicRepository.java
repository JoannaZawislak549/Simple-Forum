package com.asia.forum.boardgames.dao.impl;

import com.asia.forum.boardgames.dao.ITopicDAO;
import com.asia.forum.boardgames.model.Topic;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TopicRepository implements ITopicDAO {
    private final List<Topic> topics = new ArrayList<>();
    private int lastId = 4;

    public TopicRepository() {
        this.topics.add(
                new Topic(1, "Regulamin korzystania z forum", "admin",
                        LocalDateTime.of(2025, 6, 29, 14, 30)));
        this.topics.add(
                new Topic(2, "Zapomniane morza - nowe DLC", "asia123",
                        LocalDateTime.of(2025, 6, 30, 18, 32)));
        this.topics.add(
                new Topic(3, "Ark Nova - wasze wrażenia", "asia123",
                        LocalDateTime.of(2025, 7, 1, 12, 30)));
        this.topics.add(
                new Topic(4, "Konkurs na najciekawszą recenzję!", "ModeratorPierwszy",
                        LocalDateTime.of(2025, 7, 1, 9, 0)));
    }

    @Override
    public List<Topic> getAllTopics() {
        return this.topics;
    }

    @Override
    public Topic getTopicById(int id) {
        for(Topic topic : topics){
            if(id == topic.getId()) {
                return topic;
            }
        }
        return null;
    }

    @Override
    public void persistTopic(Topic topic) {
        topic.setId(++lastId);
        topics.add(topic);
    }

    @Override
    public Topic createTopic(String title, String author) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setAuthor(author);
        topic.setDate(LocalDateTime.now());
        persistTopic(topic);
        return topic;
    }

    @Override
    public List<Topic> getTopicsByAuthor(String author) {
        List<Topic> userTopics = new ArrayList<>();
        for (Topic topic : topics) {
            if (author.equals(topic.getAuthor())) {
                userTopics.add(topic);
            }
        }

        // Sort topics by date in descending order (most recent first), algorithm: Bubble Sort
        for (int i = 0; i < userTopics.size() - 1; i++) {
            for (int j = 0; j < userTopics.size() - i - 1; j++) {
                if (userTopics.get(j).getDate().isBefore(userTopics.get(j + 1).getDate())) {
                    Topic temp = userTopics.get(j);
                    userTopics.set(j, userTopics.get(j + 1));
                    userTopics.set(j + 1, temp);
                }
            }
        }
        return userTopics;
    }

    @Override
    public List<Topic> findTopicsByTitleContaining(String query) {
        List<Topic> matchedTopics = new ArrayList<>();
        for (Topic topic : topics) {
            if (topic.getTitle().toLowerCase().contains(query.toLowerCase())) {
                matchedTopics.add(topic);
            }
        }
        return matchedTopics;
    }

}
