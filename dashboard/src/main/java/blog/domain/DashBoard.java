package blog.domain;

import javax.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "DashBoard_table")
@Data
public class DashBoard {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private Long postId;
        private String postTitle;
        private String postContent;
        private String postNickname;

        @ElementCollection
        private List<Comment> commentList;
}
