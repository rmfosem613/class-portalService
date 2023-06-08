package kr.ac.jejunu.project.dao;

import kr.ac.jejunu.project.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private static final String INSERT_POST_QUERY="INSERT INTO POST(id,title,department,major,sPeriod,ePeriod,btitle,bauthor,content,password,nickname) values(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_POST_QUERY="UPDATE POST SET title=?, department=?, major=?, sPeriod=?, ePeriod=?, btitle=?, nickname=?, content=?, password=? WHERE id=?";
    private static final String GET_POST_QUERY="SELECT * FROM POST WHERE id=?";
    private static final String DELETE_POST_QUERY="DELETE FROM POST WHERE id=?";
    private static final String GET_POSTS_QUERY="SELECT * FROM POST";
    private static final String GET_MAJORS_QUERY = "SELECT DISTINCT major FROM POST WHERE department = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Post savePost(Post post) {
        jdbcTemplate.update(INSERT_POST_QUERY,
                post.getId(),
                post.getTitle(),
                post.getDepartment(),
                post.getMajor(),
                post.getSPeriod(),
                post.getEPeriod(),
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
                post.getDepartment(),
                post.getMajor(),
                post.getSPeriod(),
                post.getEPeriod(),
                post.getBtitle(),
                post.getNickname(),
                post.getContent(),
                post.getPassword(),
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
                    rs.getString("sPeriod"),
                    rs.getString("ePeriod"),
                    rs.getString("btitle"),
                    rs.getString("bauthor"),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getString("nickname")
            );
        }, id);
    }

    @Override
    public boolean deleteById(int id) {
        int rowsAffected = jdbcTemplate.update(DELETE_POST_QUERY,id);
        return rowsAffected > 0;
    }

    @Override
    public Post getPostById(int id) {
        return getById(id);
    }

    @Override
    public List<Post> allPosts() {
        return jdbcTemplate.query(GET_POSTS_QUERY, (rs, rowNum) -> {
            return new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("department"),
                    rs.getString("major"),
                    rs.getString("sPeriod"),
                    rs.getString("ePeriod"),
                    rs.getString("btitle"),
                    rs.getString("bauthor"),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getString("nickname")
            );
        });
    }
    @Override
    public List<String> getDepartments() {
        return jdbcTemplate.queryForList("SELECT DISTINCT department FROM POST", String.class);
    }

    @Override
    public List<String> getMajorsByDepartment(String department) {
        return jdbcTemplate.queryForList(GET_MAJORS_QUERY, String.class, department);
    }
}
