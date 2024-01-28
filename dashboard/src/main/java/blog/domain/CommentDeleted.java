package blog.domain;

import blog.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommentDeleted extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;
    private Long userId;
}
