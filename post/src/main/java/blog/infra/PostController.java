package blog.infra;

import blog.auth.authUser;
import blog.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@Transactional
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts/test") 
    public String example() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication.getPrincipal() instanceof authUser) {
            authUser userDetails = (authUser) authentication.getPrincipal();
            String id = userDetails.getId();
            String email = userDetails.getEmail();
            String nickname = userDetails.getNickname();
    
            System.out.println("ID: " + id);
            System.out.println("Email: " + email);
            System.out.println("nickname: " + nickname);
        }
        return "hello";
    }
}
