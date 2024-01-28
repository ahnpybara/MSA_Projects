package blog.domain;

import blog.infra.AbstractEvent;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class CommentCreated extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;
    private Long userId;

    public CommentCreated(Comment aggregate) {
        super(aggregate);
    }

    public CommentCreated() {
        super();
    }
}