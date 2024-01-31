package blog.infra;

import blog.auth.AuthUser;
import blog.auth.JwtAuthorizationFilter;
import blog.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Transactional
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/posts/create")
    public ResponseEntity<String> createPost(@RequestBody PostCreated postCreated) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof AuthUser) {
            AuthUser userDetails = (AuthUser) authentication.getPrincipal();

            Long id = userDetails.getId();
            String email = userDetails.getEmail();
            String nickName = userDetails.getNickName();
            System.out.println("=================================" + id);
            System.out.println("=================================" + email);
            System.out.println("================================" + nickName);

            Post post = new Post();
            post.setNickname(nickName);
            post.setUserId(id);

            post.create(postCreated);
            postRepository.save(post);
            return ResponseEntity.ok("등록완료");
        }
        return null;
    }

}
