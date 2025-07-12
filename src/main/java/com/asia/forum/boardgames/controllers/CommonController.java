package com.asia.forum.boardgames.controllers;

import com.asia.forum.boardgames.model.User;
import com.asia.forum.boardgames.model.view.ViewPost;
import com.asia.forum.boardgames.model.view.ViewTopic;
import com.asia.forum.boardgames.services.IContentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommonController {
    private final IContentService contentService;

    @GetMapping({"/main", "/", "/index"})
    public String main(Model model, HttpSession session) {
        List<ViewTopic> topics = this.contentService.showSortedTopicsByLastPost();
        HashMap<Integer, List<ViewPost>> posts = this.contentService.getAllPostsForTopics(topics);
        User loggedUser = (User) session.getAttribute("user");
        boolean isLogged = (loggedUser != null);

        if (!isLogged) {
            topics = topics.subList(0, 1);
        }

        model.addAttribute("topics", topics);
        model.addAttribute("posts", posts);
        model.addAttribute("session", session);
        return "main";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }


}
