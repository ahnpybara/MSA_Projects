package blog.infra;

import blog.domain.*;
import blog.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DashBoardViewHandler {

//<<< DDD / CQRS
    @Autowired
    private DashBoardRepository dashBoardRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostCreated_then_CREATE_1 (@Payload PostCreated postCreated) {
        try {

            if (!postCreated.validate()) return;

            // view 객체 생성
            DashBoard dashBoard = new DashBoard();
            // view 객체에 이벤트의 Value 를 set 함
            dashBoard.setPostId(postCreated.getId());
            dashBoard.setPostTitle(postCreated.getTitle());
            dashBoard.setPostContent(postCreated.getContent());
            dashBoard.setPostNickname(postCreated.getNickname());
            // view 레파지 토리에 save
            dashBoardRepository.save(dashBoard);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenCommentCreated_then_UPDATE_1(@Payload CommentCreated commentCreated) {
        try {
            if (!commentCreated.validate()) return;
                // view 객체 조회

                List<DashBoard> dashBoardList = dashBoardRepository.findByPostId(commentCreated.getPostId());
                for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setCommentList("댓글 객체");
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostEdited_then_UPDATE_2(@Payload PostEdited postEdited) {
        try {
            if (!postEdited.validate()) return;
                // view 객체 조회

                List<DashBoard> dashBoardList = dashBoardRepository.findByPostId(postEdited.getId());
                for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setPostTitle(postEdited.getTitle());
                    dashBoard.setPostContent(postEdited.getContent());
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenUserUpdated_then_UPDATE_3(@Payload UserUpdated userUpdated) {
        try {
            if (!userUpdated.validate()) return;
                // view 객체 조회

                List<DashBoard> dashBoardList = dashBoardRepository.findByPostId(userUpdated.getId());
                for(DashBoard dashBoard : dashBoardList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    dashBoard.setPostNickname(userUpdated.getNickname());
                    dashBoard.setCommentList(List<String>.valueOf(userUpdated.getNickname()));
                // view 레파지 토리에 save
                dashBoardRepository.save(dashBoard);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPostDeleted_then_DELETE_1(@Payload PostDeleted postDeleted) {
        try {
            if (!postDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            dashBoardRepository.deleteByPostId(postDeleted.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenCommentDeleted_then_DELETE_2(@Payload CommentDeleted commentDeleted) {
        try {
            if (!commentDeleted.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            dashBoardRepository.deleteByCommentList(List<String>.valueOf(commentDeleted.getId()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//>>> DDD / CQRS
}

