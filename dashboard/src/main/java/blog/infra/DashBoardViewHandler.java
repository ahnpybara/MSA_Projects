package blog.infra;

import blog.domain.*;
import blog.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

            // 수신된 게시글 생성 이벤트에 담긴 정보를 담아서 저장하기 위한 DashBoard 객체를 생성합니다.
            DashBoard dashBoard = new DashBoard();
            dashBoard.setPostId(postCreated.getId());
            dashBoard.setPostTitle(postCreated.getTitle());
            dashBoard.setPostContent(postCreated.getContent());
            dashBoard.setPostNickname(postCreated.getNickname());
            dashBoard.setPostUserId(postCreated.getUserId());
            // 생성된 대시보드 객체를 데이터베이스에 저장합니다.
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

            // 특정 게시글Id를 가진 게시글(대시보드)를 조회합니다.
            Optional<DashBoard> dashBoard = dashBoardRepository.findByPostId(commentCreated.getPostId());

            // 만약 특정 게시글id를 가진 게시글(대시보드)가 존재한다면, 해당 게시글에 댓글을 추가합니다.
            if (dashBoard.isPresent()) {
                DashBoard new_dashBoard = dashBoard.get();
                // 이벤트 객체의 정보를 수신해서 담을 댓글 객체를 선언한 뒤, 수신된 정보로 초기화를 합니다. 
                Comment comment = new Comment();
                comment.setId(commentCreated.getId());
                comment.setContent(commentCreated.getContent());
                comment.setPostId(commentCreated.getPostId());
                comment.setNickname(commentCreated.getNickname());
                comment.setUserId(commentCreated.getUserId());

                // 기존 Comment 인스턴스들을 포함하는 새로운 List<Comment>를 만든 뒤, 새로 추가될 Comment 인스턴스를 추가합니다
                List<Comment> new_commentList = new ArrayList<Comment>(new_dashBoard.getCommentList());
                new_commentList.add(comment);

                // 설정된 새로운 List<Comment>를 DashBoard의 컬렉션 필드인 commentList로 설정합니다
                new_dashBoard.setCommentList(new_commentList);
                // 변경된 사항을 데이터베이스에 저장(반영)합니다.
                dashBoardRepository.save(new_dashBoard);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 안치윤 : 게시글 수정 이벤트를 수신 받아서 대시보드 게시글의 정보를 수정 메서드
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostEdited_then_UPDATE_2(@Payload PostEdited postEdited) {
        try {
            if (!postEdited.validate())
                return;
            // 특정 게시글Id를 가진 게시글(대시보드)를 조회합니다.
            Optional<DashBoard> dashBoard = dashBoardRepository.findByPostId(postEdited.getId());

            // 만약 특정 게시글id를 가진 게시글(대시보드)가 존재한다면, 제목과 내용을 수정된 정보로 변경합니다.
            if (dashBoard.isPresent()) {
                DashBoard new_dashBoard = dashBoard.get();
                new_dashBoard.setPostTitle(postEdited.getTitle());
                new_dashBoard.setPostContent(postEdited.getContent());
                // 변경된 사항을 데이터베이스에 저장(반영)합니다.
                dashBoardRepository.save(new_dashBoard);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 안치윤 : 유저 수정 이벤트를 수신 받아서 유저 닉네임 변경을 게시글의 정보에 반영하는 메서드
    @Transactional
    @StreamListener(KafkaProcessor.INPUT)
    public void whenUserUpdated_then_UPDATE_3(@Payload UserUpdated userUpdated) {
        try {
            if (!userUpdated.validate())
                return;
            // 특정 userId를 가진 사용자가 작성한 게시글(여기선 대시보드)목록을 조회합니다. 
            List<DashBoard> dashBoard_list = dashBoardRepository.findByPostUserId(userUpdated.getId());

            // 특정 userId를 가진 사용자가 작성한 게시글(대시보드)에 닉네임을 수정된 닉네임으로 변경합니다. 
            for (DashBoard new_dashBoard : dashBoard_list) {
                new_dashBoard.setPostNickname(userUpdated.getNickname());
                dashBoardRepository.save(new_dashBoard);
            }

            // 특정 userId를 가진 사용자가 작성한 댓글이 담긴 게시글(여기선 대시보드)목록을 조회합니다.  
            List<DashBoard> dashBoard_comment_list = dashBoardRepository.findByCommentUserId(userUpdated.getId());

            // 특정 userId를 가진 사용자가 작성한 댓글(대시보드)에 닉네임을 수정된 닉네임으로 변경합니다. 
            for (DashBoard dashBoard_comment : dashBoard_comment_list) {
                for (Comment comment : dashBoard_comment.getCommentList()) {
                    if (comment.getUserId().equals(userUpdated.getId())) {
                        comment.setNickname(userUpdated.getNickname());
                    }
                }
                // 변경된 사항을 데이터베이스에 저장(반영)합니다.
                dashBoardRepository.save(dashBoard_comment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostDeleted_then_DELETE_1(@Payload PostDeleted postDeleted) {
        try {
            if (!postDeleted.validate())
                return;

            dashBoardRepository.deleteByPostId(postDeleted.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @StreamListener(KafkaProcessor.INPUT)
    public void whenCommentDeleted_then_DELETE_2(@Payload CommentDeleted commentDeleted) {
        try {
            if (!commentDeleted.validate())
                return;
            // postid로 검색한 DashBoard 엔티티를 조회
            Optional<DashBoard> dashboardOptional = dashBoardRepository.findByPostId(commentDeleted.getPostId());
            if (dashboardOptional.isPresent()) {
            DashBoard dashboard = dashboardOptional.get();
            List<Comment> comments = dashboard.getCommentList();
            // userId가 일치하는 댓글을 찾아서 삭제
            comments.removeIf(comment -> comment.getUserId().equals(commentDeleted.getUserId()));
            // 변경된 댓글 리스트를 DashBoard에 설정하고 저장
            dashboard.setCommentList(comments);
            dashBoardRepository.save(dashboard);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
