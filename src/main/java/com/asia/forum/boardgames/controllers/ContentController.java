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
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ContentController {
    private final ITopicDAO topicDAO;
    private final IPostDAO postDAO;

    @GetMapping("/new-topic")
    public String newTopic(Model model) {
        model.addAttribute("topic", new Topic());
        model.addAttribute("post", new Post());
        return "new-topic.html";
    }

    @PostMapping("/new-topic")
    public String newTopic(@ModelAttribute Topic topic, @ModelAttribute Post post,
                           @RequestParam("title") String title, @RequestParam("content") String content,
                           HttpSession session) {
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

        return "redirect:/main";
    }

    @PostMapping("/topics/{id}/reply")
    public String replyToTopic(@PathVariable("id") int topicId,
                               @RequestParam("content") String content, HttpSession session) {

        Post post = new Post();
        post.setTopicId(topicId);
        User user = (User) session.getAttribute("user");
        String author = user.getLogin();
        post.setAuthor(author);
        post.setDate(java.time.LocalDateTime.now());
        post.setContent(content);
        this.postDAO.persistPost(post);
        return "redirect:/topics/" + topicId;
    }
}
