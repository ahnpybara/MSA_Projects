package blog.infra;

import blog.config.kafka.KafkaProcessor;
import blog.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    CommentRepository commentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PostDeleted'"
    )
    public void wheneverPostDeleted_DeleteAllComments(
        @Payload PostDeleted postDeleted
    ) {
        PostDeleted event = postDeleted;
        System.out.println(
            "\n\n##### listener DeleteAllComments : " + postDeleted + "\n\n"
        );

        Comment.deleteAllComments(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='UserUpdated'"
    )
    public void wheneverUserUpdated_UpdateUser(
        @Payload UserUpdated userUpdated
    ) {
        UserUpdated event = userUpdated;
        System.out.println(
            "\n\n##### listener UpdateUser : " + userUpdated + "\n\n"
        );

        Comment.updateUser(event);
    }
}
