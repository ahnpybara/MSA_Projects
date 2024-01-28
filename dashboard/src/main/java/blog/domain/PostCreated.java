package blog.domain;

import blog.infra.AbstractEvent;
import java.util.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PostCreated extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private List<Long> commentList;
    private String nickname;
    private Long userId;
}
