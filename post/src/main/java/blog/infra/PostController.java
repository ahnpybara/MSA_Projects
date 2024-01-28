package blog.infra;

import blog.domain.*;
<<<<<<< HEAD
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/posts")
=======
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
@Transactional
public class PostController {

    @Autowired
    PostRepository postRepository;
}
<<<<<<< HEAD
//>>> Clean Arch / Inbound Adaptor
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
