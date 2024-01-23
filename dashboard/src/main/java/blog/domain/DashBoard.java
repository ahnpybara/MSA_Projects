package blog.domain;

import javax.persistence.*;
import java.util.List;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name="DashBoard_table")
@Data
public class DashBoard {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long postId;
        private String postTitle;
        private String postContent;
        private String postNickname;
        // 안치윤 : 어노테이션 추가
        @ElementCollection
        private List<Comment> commentList;
}
