package blog.domain;

import blog.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PostEdited extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private String nickname;
}
