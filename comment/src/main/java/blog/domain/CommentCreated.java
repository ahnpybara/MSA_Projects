package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CommentCreated extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;

    public CommentCreated(Comment aggregate) {
        super(aggregate);
    }

    public CommentCreated() {
        super();
    }
}
//>>> DDD / Domain Event
