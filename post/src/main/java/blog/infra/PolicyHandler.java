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
    PostRepository postRepository;

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CommentCreated'"
    )
    public void wheneverCommentCreated_CommentCreateOnPost(
        @Payload CommentCreated commentCreated
    ) {
        CommentCreated event = commentCreated;
        System.out.println(
            "\n\n##### listener CommentCreateOnPost : " +
            commentCreated +
            "\n\n"
        );

        Post.commentCreateOnPost(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CommentDeleted'"
    )
    public void wheneverCommentDeleted_CommentDeleteOnPost(
        @Payload CommentDeleted commentDeleted
    ) {
        CommentDeleted event = commentDeleted;
        System.out.println(
            "\n\n##### listener CommentDeleteOnPost : " +
            commentDeleted +
            "\n\n"
        );

        Post.commentDeleteOnPost(event);
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

        Post.updateUser(event);
    }
}
