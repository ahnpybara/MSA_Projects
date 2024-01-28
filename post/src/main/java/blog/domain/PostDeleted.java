package blog.domain;

import blog.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class PostDeleted extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private String nickname;
    private Long userId;

    public PostDeleted(Post aggregate) {
        super(aggregate);
    }

    public PostDeleted() {
        super();
    }
}
//>>> DDD / Domain Event