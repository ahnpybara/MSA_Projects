package blog.domain;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

<<<<<<< HEAD
//<<< PoEAA / Repository
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
@RepositoryRestResource(collectionResourceRel = "posts", path = "posts")
public interface PostRepository
    extends PagingAndSortingRepository<Post, Long> {

    List<Post> findByUserId(Long id);
}