package kr.ac.jejunu.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private int id;
    private String title;
    private String department;
    private String major;
    private String period;
    private String btitle;
    private String bauthor;
    private String content;
    private String password;
    private String nickname;
}
