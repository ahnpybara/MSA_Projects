package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PostEdited extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private List<Long> commentList;
    private String nickname;

    public PostEdited(Post aggregate) {
        super(aggregate);
    }

    public PostEdited() {
        super();
    }
}
//>>> DDD / Domain Event
