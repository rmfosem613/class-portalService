package kr.ac.jejunu.project.controller;

import kr.ac.jejunu.project.dao.PostRepository;
import kr.ac.jejunu.project.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    @PostMapping("/create/save")
    public String addPost(@ModelAttribute("post") Post post) {

        postRepository.savePost(post);
        return "redirect:/posts";
    }

    @GetMapping("/post/edit/{id}")
    public String updatePostForm(@PathVariable("id") int id, Model model) {
        Post post = postRepository.getPostById(id);
        model.addAttribute("post", post);
        return "update"; // 수정 폼으로 이동하는 뷰 이름을 반환해야 합니다.
    }

    @PostMapping("/post/edit/{id}")
    public String updatePost(@PathVariable("id") int id, @ModelAttribute("post") Post post) {
        post.setId(id);
        postRepository.updatePost(post);
        return "redirect:/post/{id}";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable("id") int id, Model model) {
        Post post = postRepository.getById(id);
        model.addAttribute("post", post);
        return "view";
    }

    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> listPosts = postRepository.allPosts();
        model.addAttribute("listPosts", listPosts);
        return "posts";
    }

    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") int id) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
