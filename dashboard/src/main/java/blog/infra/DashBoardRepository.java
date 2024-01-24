package blog.infra;

import blog.domain.*;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "dashBoards",
    path = "dashBoards"
)
public interface DashBoardRepository
    extends PagingAndSortingRepository<DashBoard, Long> {

    void deleteByPostId(Long postId);
    // 안치윤 : postId를 기반으로 한 개의 대시보드를 찾기 위한 메서드를 별도로 선언
    Optional<DashBoard> findByPostId(Long postId);
}
