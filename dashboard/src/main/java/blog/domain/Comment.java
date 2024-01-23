package blog.domain;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
// 안치윤 : 댓글 객체 자체를 리스트로 담기 위한 Comment 클래스를 별도로 선언
public class Comment {
    private Long id;
    private String content;
    private Long postId;
    private String nickname;
}