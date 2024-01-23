package blog.domain;

import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class CommentDeleted extends AbstractEvent {

    private Long id;
    private String content;
    private Long postId;
    private String nickname;
}
