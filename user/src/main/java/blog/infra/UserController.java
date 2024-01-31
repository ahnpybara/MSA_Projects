package blog.infra;

import blog.domain.*;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@Transactional
@RequiredArgsConstructor
public class UserController {
    
    private final UserRepository userRepository;
        
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody UserCreated userCreated){

        //객체 생성
        User user = new User();
        //객체 생성 후 해당 패스워드는 암호화된 아이디로 저장
        user.register(userCreated);

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }       
}