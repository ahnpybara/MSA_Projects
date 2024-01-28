package blog.infra;

import blog.config.kafka.KafkaProcessor;
import blog.domain.*;
<<<<<<< HEAD
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
//<<< Clean Arch / Inbound Adaptor
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    PostRepository postRepository;

<<<<<<< HEAD
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
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

<<<<<<< HEAD
        // Sample Logic //
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
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

<<<<<<< HEAD
        // Sample Logic //
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
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

<<<<<<< HEAD
        // Sample Logic //
        Post.updateUser(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
=======
        Post.updateUser(event);
    }
}
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
