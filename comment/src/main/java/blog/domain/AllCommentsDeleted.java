package blog.domain;

import blog.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class AllCommentsDeleted extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;
    private Long userId;

    public AllCommentsDeleted(List<Comment> aggregate) {
        super(aggregate);
    }

    public AllCommentsDeleted() {
        super();
    }
}
