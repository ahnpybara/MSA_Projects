package blog.domain;

import blog.infra.AbstractEvent;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class CommentDeleted extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;
    private Long userId;

    public CommentDeleted(Comment aggregate) {
        super(aggregate);
    }

    public CommentDeleted() {
        super();
    }
}
