package com.asia.forum.boardgames.controllers;

import com.asia.forum.boardgames.model.Post;
import com.asia.forum.boardgames.model.Topic;
import com.asia.forum.boardgames.model.User;
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
    public String newTopic() {
        return "new-topic";
    }

    @PostMapping("/new-topic")
    public String newTopic(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Topic topic = this.contentService.createTopic(title, user.getLogin());
        this.contentService.createReply(content, topic.getId(), user.getLogin());

        return "redirect:/main";
    }

    @PostMapping("/topics/{id}/reply")
    public String replyToTopic(@PathVariable("id") int topicId,
                              @RequestParam("content") String content,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        this.contentService.createReply(content, topicId, user.getLogin());
        return "redirect:/topics/" + topicId;
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String query, Model model) {
        List<ViewTopic> topics = this.contentService.searchTopicOrPost(query);
        model.addAttribute("topics", topics);
        model.addAttribute("posts", this.contentService.getAllPostsForTopics(topics));
        return "main";
    }

    @PostMapping("/topics/{topicId}/posts/{postId}/edit")
    public String editPost(@PathVariable int topicId,
                         @PathVariable int postId,
                         @RequestParam String content,
                         HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Post post = this.contentService.getPost(topicId, postId);
        if (post != null && post.getAuthor().equals(user.getLogin())) {
            this.contentService.updatePost(topicId, postId, content);
        }
        return "redirect:/topics/" + topicId;
    }

    @PostMapping("/topics/{topicId}/posts/{postId}/delete")
    public String deletePost(@PathVariable int topicId,
                           @PathVariable int postId,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Post post = this.contentService.getPost(topicId, postId);
        if (post != null && post.getAuthor().equals(user.getLogin())) {
            this.contentService.deletePost(topicId, postId);
        }
        return "redirect:/topics/" + topicId;
    }

    @GetMapping("/topics/{topicId}/posts/{postId}/edit")
    public String showEditPostForm(@PathVariable int topicId,
                                 @PathVariable int postId,
                                 Model model,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Post post = this.contentService.getPost(topicId, postId);
        if (post == null || !post.getAuthor().equals(user.getLogin())) {
            return "redirect:/topics/" + topicId;
        }

        model.addAttribute("post", post);
        model.addAttribute("topicId", topicId);
        return "edit-post";
    }
}
