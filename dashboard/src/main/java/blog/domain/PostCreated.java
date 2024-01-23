package blog.domain;

import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class PostCreated extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private List<Long> commentList;
    private String nickname;
}
