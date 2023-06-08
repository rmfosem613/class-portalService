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

    @GetMapping("/posts/{category}")
    public String getPostsByCategory(@PathVariable("category") String category, Model model) {
        List<Post> postsByCategory = postRepository.getPostsByCategory(category);
        model.addAttribute("listPosts", postsByCategory);
        model.addAttribute("category", category);
        return "posts";
    }

    @GetMapping("/post/edit/{id}")
    public String updatePostForm(@PathVariable("id") int id, Model model) {
        Post post = postRepository.getPostById(id);
        model.addAttribute("post", post);

        List<String> departments = postRepository.getDepartments();
        model.addAttribute("departments", departments);

        List<String> majors = postRepository.getMajorsByDepartment(post.getDepartment());
        model.addAttribute("majors", majors);
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
        // 비밀번호도 모델에 추가
        model.addAttribute("password", post.getPassword());
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
        boolean deleted = postRepository.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
