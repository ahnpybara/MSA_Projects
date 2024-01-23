package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CommentDeleted extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;

    public CommentDeleted(Comment aggregate) {
        super(aggregate);
    }

    public CommentDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
