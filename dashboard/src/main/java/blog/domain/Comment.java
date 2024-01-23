package blog.domain;

import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
// 안치윤 : 댓글 객체 자체를 저장하기 위한 별도의 클래스를 선언
public class Comment {
    private Long id;
    private String content;
    private Long postId;
    private String nickname;
}