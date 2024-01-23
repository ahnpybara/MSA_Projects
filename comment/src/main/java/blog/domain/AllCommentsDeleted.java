package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class AllCommentsDeleted extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;

    public AllCommentsDeleted(Comment aggregate) {
        super(aggregate);
    }

    public AllCommentsDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
