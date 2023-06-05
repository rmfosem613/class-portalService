package kr.ac.jejunu.project.dao;

import kr.ac.jejunu.project.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private static final String INSERT_POST_QUERY="INSERT INTO POST(id,title,department,major,period,btitle,bauthor,content,password,nickname) values(?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_POST_QUERY="UPDATE POST SET title=? WHERE id=?";
    private static final String GET_POST_QUERY="SELECT * FROM POST WHERE id=?";
    private static final String DELETE_POST_QUERY="DELETE FROM POST WHERE id=?";
    private static final String GET_POSTS_QUERY="SELECT * FROM POST";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Post savePost(Post post) {
        jdbcTemplate.update(INSERT_POST_QUERY,
                post.getId(),
                post.getTitle(),
                post.getDepartment(),
                post.getMajor(),
                post.getPeriod(),
                post.getBtitle(),
                post.getBauthor(),
                post.getContent(),
                post.getPassword(),
                post.getNickname()
        );
        return post;
    }

    @Override
    public Post updatePost(Post post) {
        jdbcTemplate.update(UPDATE_POST_QUERY,
                post.getTitle(),
                post.getId()
        );
        return post;
    }

    @Override
    public Post getById(int id) {
        return jdbcTemplate.queryForObject(GET_POST_QUERY,(rs, rowNum) -> {
            return new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("department"),
                    rs.getString("major"),
                    rs.getString("period"),
                    rs.getString("btitle"),
                    rs.getString("bauthor"),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getString("nickname")
            );
        }, id);
    }

    @Override
    public String deleteById(int id) {
        jdbcTemplate.update(DELETE_POST_QUERY,id);
        return "Post got deleted with id"+id;
    }

    @Override
    public List<Post> allPosts() {
        return jdbcTemplate.query(GET_POSTS_QUERY, (rs, rowNum) -> {
            return new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("department"),
                    rs.getString("major"),
                    rs.getString("period"),
                    rs.getString("btitle"),
                    rs.getString("bauthor"),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getString("nickname")
            );
        });
    }
}
