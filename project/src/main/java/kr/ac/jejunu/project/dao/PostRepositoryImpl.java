package kr.ac.jejunu.project.dao;

import kr.ac.jejunu.project.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private static final String INSERT_POST_QUERY="INSERT INTO POST(id,number,title,department,major,sPeriod,ePeriod,btitle,bauthor,content,password,nickname) values(?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_POST_QUERY="UPDATE POST SET number=?, title=?, department=?, major=?, sPeriod=?, ePeriod=?, btitle=?, nickname=?, content=?, password=?, star=? WHERE id=?";
    private static final String GET_POST_QUERY="SELECT * FROM POST WHERE id=?";
    private static final String DELETE_POST_QUERY="DELETE FROM POST WHERE id=?";
    private static final String GET_POSTS_QUERY="SELECT * FROM POST";
    private static final String GET_MAJORS_QUERY = "SELECT DISTINCT major FROM POST WHERE department = ?";
    private static final String GET_POSTS_PAGE_QUERY="SELECT * FROM POST ORDER BY number ASC LIMIT ? OFFSET ?";
    private static final String UPDATE_NUMBERS_AFTER_DELETION_QUERY="UPDATE POST SET number = number - 1 WHERE number > ?";
    private static final String MAX_POST_NUMBER_QUERY = "SELECT MAX(number) FROM POST";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Post savePost(Post post) {
        Integer maxNumber = jdbcTemplate.queryForObject(MAX_POST_NUMBER_QUERY, Integer.class);
        post.setNumber((maxNumber != null ? maxNumber : 0) + 1);

        jdbcTemplate.update(INSERT_POST_QUERY,
                post.getId(),
                post.getNumber(),
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
        // 데이터베이스에서 기존 게시물을 가져오기
        Post existingPost = getById(post.getId());
        // 'number' 값을 기존 게시물의 'number' 값으로 설정
        post.setNumber(existingPost.getNumber());

        jdbcTemplate.update(UPDATE_POST_QUERY,
                post.getNumber(),
                post.getTitle(),
                post.getDepartment(),
                post.getMajor(),
                post.getSPeriod(),
                post.getEPeriod(),
                post.getBtitle(),
                post.getNickname(),
                post.getContent(),
                post.getPassword(),
                post.getStar(),
                post.getId()
        );
        return post;
    }

    @Override
    public Post getById(int id) {
        return jdbcTemplate.queryForObject(GET_POST_QUERY,(rs, rowNum) -> {
            return new Post(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("title"),
                    rs.getString("department"),
                    rs.getString("major"),
                    rs.getString("sPeriod"),
                    rs.getString("ePeriod"),
                    rs.getString("btitle"),
                    rs.getString("bauthor"),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getInt("star")
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
                    rs.getInt("number"),
                    rs.getString("title"),
                    rs.getString("department"),
                    rs.getString("major"),
                    rs.getString("sPeriod"),
                    rs.getString("ePeriod"),
                    rs.getString("btitle"),
                    rs.getString("bauthor"),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getInt("star")
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

    @Override
    public Page<Post> getPostsByCategory(String category, Pageable pageable) {
        String countQuery = "SELECT count(*) FROM POST WHERE department = ?";
        String query = "SELECT * FROM POST WHERE department = ? ORDER BY id ASC LIMIT ? OFFSET ?";
        Integer total = jdbcTemplate.queryForObject(countQuery, new Object[]{category}, Integer.class);
        List<Post> posts = jdbcTemplate.query(query, (rs, rowNum) -> {
            return new Post(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("title"),
                    rs.getString("department"),
                    rs.getString("major"),
                    rs.getString("sPeriod"),
                    rs.getString("ePeriod"),
                    rs.getString("btitle"),
                    rs.getString("bauthor"),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getInt("star")
            );
        }, category, pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(posts, pageable, total != null ? total : 0);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        int total = allPosts().size(); // 전체 게시물 수 계산
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        List<Post> posts = jdbcTemplate.query(GET_POSTS_PAGE_QUERY, (rs, rowNum) -> {
            return new Post(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("title"),
                    rs.getString("department"),
                    rs.getString("major"),
                    rs.getString("sPeriod"),
                    rs.getString("ePeriod"),
                    rs.getString("btitle"),
                    rs.getString("bauthor"),
                    rs.getString("content"),
                    rs.getString("password"),
                    rs.getString("nickname"),
                    rs.getInt("star")
            );
        }, pageSize, pageNumber * pageSize);
        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public void updateNumbersAfterDeletion(int number) {
        jdbcTemplate.update(UPDATE_NUMBERS_AFTER_DELETION_QUERY, number);
    }

    @Override
    public Optional<Integer> findMaxNumber() {
        Integer maxNumber = jdbcTemplate.queryForObject(MAX_POST_NUMBER_QUERY, Integer.class);
        return Optional.ofNullable(maxNumber);
    }

    @Override
    public void increaseStarById(int id) {
        String INCREASE_STAR_QUERY = "UPDATE POST SET star = star + 1 WHERE id = ?";
        jdbcTemplate.update(INCREASE_STAR_QUERY, id);
    }
}
