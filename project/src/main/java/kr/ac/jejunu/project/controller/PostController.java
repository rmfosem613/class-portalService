package kr.ac.jejunu.project.controller;

import kr.ac.jejunu.project.dao.PostRepository;
import kr.ac.jejunu.project.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;

    @PostMapping("/post")
    public Post addPost(@RequestBody Post post) {
        return postRepository.savePost(post);
    }

    @PutMapping("/post")
    public Post updatePost(@RequestBody Post post) {
        return postRepository.updatePost(post);
    }

    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable("id") int id) {
        return postRepository.getById(id);
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postRepository.allPosts();
    }

    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable("id") int id) {
        return postRepository.deleteById(id);
    }
}
