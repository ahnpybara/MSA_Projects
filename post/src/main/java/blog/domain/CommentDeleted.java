package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class CommentDeleted extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;
}
