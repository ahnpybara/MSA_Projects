package blog.domain;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "posts", path = "posts")
public interface PostRepository
    extends PagingAndSortingRepository<Post, Long> {

    List<Post> findByUserId(Long id);
}