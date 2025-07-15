package com.asia.forum.boardgames.controllers;

import com.asia.forum.boardgames.exceptions.LoginTakenException;
import com.asia.forum.boardgames.exceptions.UserValidationException;
import com.asia.forum.boardgames.model.User;
import com.asia.forum.boardgames.model.view.ViewPost;
import com.asia.forum.boardgames.model.view.ViewTopic;
import com.asia.forum.boardgames.services.IContentService;
import com.asia.forum.boardgames.services.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@SessionAttributes("user")
public class CommonController {
    private final IContentService contentService;
    private final IUserService userService;

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

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session){
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    public String editProfile(@RequestParam String login,
                            @RequestParam String email,
                            @RequestParam(required = false) String password,
                            @RequestParam(required = false) String confirmPassword,
                            HttpSession session,
                            Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            if (!user.getLogin().equals(login)) {
                this.userService.updateLogin(user, login);
            }

            this.userService.updateEmail(user, email);

            if (password != null && !password.isEmpty()) {
                this.userService.updatePassword(user, password, confirmPassword);
            }

            session.setAttribute("user", user);
            return "redirect:/main";
        } catch (LoginTakenException | UserValidationException e) {
            model.addAttribute("error", e.getMessage());
            return "edit-profile";
        }
    }


}
