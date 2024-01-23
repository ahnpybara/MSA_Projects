package blog.domain;

import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Comment {
    private Long id;
    private String content;
    private Long postId;
    private String nickname;
}