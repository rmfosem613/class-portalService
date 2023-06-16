package kr.ac.jejunu.project.controller;

import kr.ac.jejunu.project.dao.PostRepository;
import kr.ac.jejunu.project.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository; // 의존성 주입

    @PostMapping("/create/save")
    public String addPost(@ModelAttribute("post") Post post) { // post 파타미터를 Post 타입의 객체에 바인딩

        postRepository.savePost(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{category}") // {category}는 경로 변수를 나타내며, 실제 요청에서는 실제 카테고리 값으로 치환됨
    public String getPostsByCategory(@PathVariable("category") String category, // 해당 url에서 {category} 부분을 추출하여 category 매개변수에 바인딩.
                                     @RequestParam(defaultValue = "0") int page, // page 파라미터를 추출하여 page 매개변수에 바인딩. 만약 page 파라미터가 없는 경우, 0 할당.
                                     Model model) {
        Page<Post> postsByCategory = postRepository.getPostsByCategory(category, PageRequest.of(page, 6)); // 한 페이지에 6개의 게시글 포함
        model.addAttribute("listPosts", postsByCategory); // 해당 카테고리의 게시글 페이지
        model.addAttribute("category", category); // 현재 카테고리
        return "posts";
    }

    @GetMapping("/post/edit/{id}") // 경로변수 활용
    public String updatePostForm(@PathVariable("id") int id, // 해당 url에서 {id} 부분을 추출해 id 매개변수에 바인딩
                                 Model model) {
        Post post = postRepository.getPostById(id); // getPostById 메서드 호출하여 id에 해당하는 게시글 가져옴.
        model.addAttribute("post", post); // "post"는 현재 수정하려는 게시글을 나타냄

        List<String> departments = postRepository.getDepartments(); // 학과 목록 추가
        model.addAttribute("departments", departments);

        List<String> majors = postRepository.getMajorsByDepartment(post.getDepartment()); // 전공 목록 추가
        model.addAttribute("majors", majors);
        return "update"; // 수정 페이지 반환
    }

    @PostMapping("/post/edit/{id}") // 경로변수 활용
    public String updatePost(@PathVariable("id") int id,
                             @ModelAttribute("post") Post post) { // 수정된 게시글 정보를 담은 Post 객체 바인딩
        post.setId(id);
        postRepository.updatePost(post); // 수정된 Post 객체를 저장소에 반영하는 메소드
        return "redirect:/post/{id}";
    }

    @GetMapping("/post/{id}") // 경로변수 활용
    public String getPost(@PathVariable("id") int id, Model model) {
        Post post = postRepository.getById(id); // 해당 id를 가진 게시글을 저장소에서 조회하는 메소드 호출
        model.addAttribute("post", post); // 가져온 게시글 전체를 뷰에 전달하기 위해 모델 객체에 추가
        model.addAttribute("password", post.getPassword()); // 비밀번호도 모델 객체에 추가
        return "view";
    }

    @GetMapping("/posts")
    public String getPosts(Model model,
                           @RequestParam(defaultValue = "0") int page) { // page 파라미터를 page 매개변수에 바인딩. 기본값은 0.
        Page<Post> listPosts = postRepository.findAll(PageRequest.of(page, 6)); // 한 페이지당 6개의 게시글 불러옴.
        model.addAttribute("listPosts", listPosts); // 게시글 리스트를 뷰에 전달하기 위해 모델 객체에 추가
        return "posts";
    }

    @DeleteMapping("/post/delete/{id}") // 경로 변수 활용
    public ResponseEntity<Void> deletePost(@PathVariable("id") int id) {
        Post post = postRepository.getById(id); // 삭제할 게시글의 정보를 가져오는 메소드 호출
        int postNumber = post.getNumber(); // 삭제할 게시글의 번호를 가져와서 삭제 이후 게시글 번호를 업데이트할 때 사용.
        boolean deleted = postRepository.deleteById(id); // 게시글을 삭제하는 메소드 호출. 성공 여부를 boolean 값으로 반환.
        if (deleted) { // 삭제에 성공한 경우
            postRepository.updateNumbersAfterDeletion(postNumber); // 게시글 번호 업데이트
            return ResponseEntity.ok().build(); // 상태코드 : 200
        } else {
            return ResponseEntity.notFound().build(); // 상태고드 : 404
        }
    }

    @PostMapping("/post/{id}/star") // 경로 변수 활용
    public ResponseEntity<Post> increaseStar(@PathVariable("id") int id) {
        Post post = postRepository.getById(id); // 좋아요를 추가할 게시글의 정보를 가져오는 메소드 호출
        if (post != null) { // 게시글이 존재한다면
            postRepository.increaseStarById(id); // 좋아요 점수 증가
            post = postRepository.getById(id); // 업데이트된 star 값을 반영하기 위해 다시 불러옴.
            return ResponseEntity.ok(post); // 상태코드 : 200
        } else {
            return ResponseEntity.notFound().build(); // 상태코드 : 404
        }
    }


}
