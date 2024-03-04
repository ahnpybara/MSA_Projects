package blog.infra;

import blog.auth.AuthUser;
import blog.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/comments/create")
    public ResponseEntity<String> createPost(@RequestBody CommentCreated commentCreated) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof AuthUser) {
            AuthUser userDetails = (AuthUser) authentication.getPrincipal();

            Long id = userDetails.getId();
            String email = userDetails.getEmail();
            String nickName = userDetails.getNickName();
            System.out.println("=================================" + id);
            System.out.println("=================================" + email);
            System.out.println("================================" + nickName);

            Comment comment = new Comment();
            comment.setNickname(nickName);
            comment.setUserId(id);

            comment.create(commentCreated);
            commentRepository.save(comment);
            return ResponseEntity.ok("등록완료");
        }
        return null;
    }
}