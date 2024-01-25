package blog.infra;

import blog.domain.*;
import blog.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DashBoardViewHandler {

    // <<< DDD / CQRS
    @Autowired
    private DashBoardRepository dashBoardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostCreated_then_CREATE_1(@Payload PostCreated postCreated) {
        try {

            if (!postCreated.validate())
                return;

            // view 객체 생성
            DashBoard dashBoard = new DashBoard();
            // view 객체에 이벤트의 Value 를 set 함
            dashBoard.setPostId(postCreated.getId());
            dashBoard.setPostTitle(postCreated.getTitle());
            dashBoard.setPostContent(postCreated.getContent());
            dashBoard.setPostNickname(postCreated.getNickname());
            // view 레파지 토리에 save
            dashBoardRepository.save(dashBoard);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 안치윤 : 댓글 생성 이벤트를 수신 받아서 대시보드 게시글에 생성된 댓글을 추가하는 메서드
    @Transactional
    @StreamListener(KafkaProcessor.INPUT)
    public void whenCommentCreated_then_UPDATE_1(@Payload CommentCreated commentCreated) {
        try {
            if (!commentCreated.validate())
                return;

            Optional<DashBoard> dashBoard = dashBoardRepository.findByPostId(commentCreated.getPostId());

            if (dashBoard.isPresent()) {
                DashBoard new_dashBoard = dashBoard.get();
                Comment comment = new Comment();
                comment.setId(commentCreated.getId());
                comment.setContent(commentCreated.getContent());
                comment.setPostId(commentCreated.getPostId());
                comment.setNickname(commentCreated.getNickname());

                // 기존 Comment 인스턴스들을 포함하는 새로운 List<Comment>를 만든다.
                List<Comment> new_commentList = new ArrayList<Comment>(new_dashBoard.getCommentList());
                new_commentList.add(comment);

                // 이 새로운 List<Comment>를 DashBoard 엔티티에 설정한다.
                new_dashBoard.setCommentList(new_commentList);
                dashBoardRepository.save(new_dashBoard);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostEdited_then_UPDATE_2(@Payload PostEdited postEdited) {
        try {
            if (!postEdited.validate())
                return;
            // view 객체 조회

            // List<DashBoard> dashBoardList = dashBoardRepository.findByPostId(postEdited.getId());
            // for (DashBoard dashBoard : dashBoardList) {
            //     // view 객체에 이벤트의 eventDirectValue 를 set 함
            //     dashBoard.setPostTitle(postEdited.getTitle());
            //     dashBoard.setPostContent(postEdited.getContent());
            //     // view 레파지 토리에 save
            //     dashBoardRepository.save(dashBoard);
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenUserUpdated_then_UPDATE_3(@Payload UserUpdated userUpdated) {
        try {
            if (!userUpdated.validate())
                return;
            // view 객체 조회

            // List<DashBoard> dashBoardList = dashBoardRepository.findByPostId(userUpdated.getId());
            // for (DashBoard dashBoard : dashBoardList) {
            //     // view 객체에 이벤트의 eventDirectValue 를 set 함
            //     dashBoard.setPostNickname(userUpdated.getNickname());
            //     dashBoard.setCommentList(null);
            //     // view 레파지 토리에 save
            //     dashBoardRepository.save(dashBoard);
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostDeleted_then_DELETE_1(@Payload PostDeleted postDeleted) {
        try {
            if (!postDeleted.validate())
                return;
            // view 레파지 토리에 삭제 쿼리
            dashBoardRepository.deleteByPostId(postDeleted.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenCommentDeleted_then_DELETE_2(@Payload CommentDeleted commentDeleted) {
        try {
            if (!commentDeleted.validate())
                return;
            // view 레파지 토리에 삭제 쿼리
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // >>> DDD / CQRS
}
