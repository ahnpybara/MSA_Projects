package blog.infra;

import blog.domain.*;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "dashBoards", path = "dashBoards")
public interface DashBoardRepository
        extends PagingAndSortingRepository<DashBoard, Long> {

    void deleteByPostId(Long postId);

    // 안치윤 : 별도의 메서드를 선언
    Optional<DashBoard> findByPostId(Long postId);

    Optional<DashBoard> findByPostNickname(String nickname);

    List<DashBoard> findByPostUserId(Long userId);

    @Query("SELECT d FROM DashBoard d JOIN d.commentList c WHERE c.userId = :userId")
    List<DashBoard> findByCommentUserId(Long userId);
}
