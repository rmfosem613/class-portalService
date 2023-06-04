package kr.ac.jejunu.project.dao;

import kr.ac.jejunu.project.entity.Post;

import java.util.List;

public interface PostRepository{
    Post savePost(Post post);
    Post updatePost(Post post);
    Post getById(int id);
    String deleteById(int id);
    List<Post> allPosts();
}
