package kr.ac.jejunu.project.dao;

import kr.ac.jejunu.project.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository{
    Post savePost(Post post);
    Post updatePost(Post post);
    Post getById(int id);
    boolean deleteById(int id);
    List<Post> allPosts();
    List<String> getDepartments();

    List<String> getMajorsByDepartment(String department);
    Post getPostById(int id);

    Page<Post> getPostsByCategory(String category, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
    void updateNumbersAfterDeletion(int number);

    Optional<Integer> findMaxNumber();

    void increaseStarById(int id);
}
