package com.asia.forum.boardgames.controllers;

import com.asia.forum.boardgames.dao.IPostDAO;
import com.asia.forum.boardgames.dao.ITopicDAO;
import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import com.asia.forum.boardgames.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CommonController {
    private final ITopicDAO topicDAO;
    private final IPostDAO postDAO;


    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        List<Topic> topics = this.topicDAO.getAllTopics();
        User loggedUser = (User) session.getAttribute("user");
        boolean isLogged = (loggedUser != null);

        //Sort topics by latest post date
        topics.sort((t1, t2) -> {
            LocalDateTime d1 = this.postDAO.getLatestPostForTopicId(t1.getId()).getDate();
            LocalDateTime d2 = this.postDAO.getLatestPostForTopicId(t2.getId()).getDate();

            if (d1 == null) d1 = t1.getDate();
            if (d2 == null) d2 = t2.getDate();

            return d2.compareTo(d1);
        });

        if (!isLogged) {
            topics = topics.stream().limit(1).toList();
        }
        model.addAttribute("topics", topics);
        model.addAttribute("posts", this.postDAO);
        return "main";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/topics/{id}")
    public String viewTopic(@PathVariable int id, Model model) {
        Topic topic = this.topicDAO.getTopicById(id);
        model.addAttribute("topic", topic);
        model.addAttribute("posts", this.postDAO.getAllPostsForTopicId(id));
        return "topic";
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String query, Model model, HttpSession session) {
        // Szukaj w tematach
        List<Topic> allTopics = this.topicDAO.getAllTopics();
        List<Topic> matchedTopics = allTopics.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();

        // Szukaj w postach
        List<Topic> topicsWithMatchingPosts = allTopics.stream()
                .filter(topic -> this.postDAO.getAllPostsForTopicId(topic.getId()) != null &&
                        this.postDAO.getAllPostsForTopicId(topic.getId()).stream()
                                .anyMatch(post -> post.getContent().toLowerCase().contains(query.toLowerCase())))
                .toList();

        // Połącz wyniki (unikalne tematy)
        List<Topic> results = new java.util.ArrayList<>(matchedTopics);
        for (Topic t : topicsWithMatchingPosts) {
            if (!results.contains(t)) results.add(t);
        }

        model.addAttribute("topics", results);
        model.addAttribute("searchQuery", query);
        model.addAttribute("posts", this.postDAO);
        model.addAttribute("session", session);
        return "main";
    }
}
