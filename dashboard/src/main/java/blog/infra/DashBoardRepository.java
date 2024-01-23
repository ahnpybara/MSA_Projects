package blog.infra;

import blog.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "dashBoards",
    path = "dashBoards"
)
public interface DashBoardRepository
    extends PagingAndSortingRepository<DashBoard, Long> {
    List<DashBoard> findByPostId(Long postId);

    void deleteByPostId(Long postId);
    void deleteByCommentList(List<String> commentList);
}
