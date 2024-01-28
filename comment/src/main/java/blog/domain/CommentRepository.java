package blog.domain;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "comments", path = "comments")
public interface CommentRepository
    extends PagingAndSortingRepository<Comment, Long> {

    List<Comment> findByUserId(Long id);

    List<Comment> findAllByPostId(Long id);}