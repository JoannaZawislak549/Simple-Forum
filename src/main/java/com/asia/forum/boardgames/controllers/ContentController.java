package com.asia.forum.boardgames.controllers;

import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import com.asia.forum.boardgames.model.view.ViewTopic;
import com.asia.forum.boardgames.services.IContentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ContentController {
    private final IContentService contentService;

    @GetMapping("/topics/{id}")
    public String viewTopic(@PathVariable int id, Model model) {
        model.addAttribute("topic", this.contentService.getTopicById(id));
        model.addAttribute("posts", this.contentService.getAllPostsForTopicId(id));
        return "topic";
    }

    @GetMapping("/new-topic")
    public String newTopic(Model model) {
        model.addAttribute("topic", new Topic());
        model.addAttribute("post", new Post());
        return "new-topic.html";
    }

    @PostMapping("/new-topic")
    public String newTopic(@ModelAttribute Topic topic, @ModelAttribute Post post,
                           @RequestParam("title") String title, @RequestParam("content") String content) {

        //create a new topic and post
        this.contentService.createTopic(topic, post, title, content);
        return "redirect:/main";
    }

    @PostMapping("/topics/{id}/reply")
    public String replyToTopic(@PathVariable("id") int topicId,
                               @RequestParam("content") String content) {

        //Reply to a topic
        this.contentService.createReply(content, topicId);
        return "redirect:/topics/" + topicId;
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String query, Model model) {
        List<ViewTopic> topics = this.contentService.searchTopicOrPost(query);
        model.addAttribute("topics", topics);
        model.addAttribute("posts", this.contentService.getAllPostsForTopics(topics));
        return "main";
    }
}
