package jejunu.ac.kr.userdao3;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/user/{id}")
    public User get(@PathVariable Long id) {
        return userDao.findById(id);
    }
}
