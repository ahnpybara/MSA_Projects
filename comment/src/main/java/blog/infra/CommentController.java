package blog.infra;

import blog.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Transactional
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
}