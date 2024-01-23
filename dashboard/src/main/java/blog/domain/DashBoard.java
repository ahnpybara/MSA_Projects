package blog.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;
import java.time.LocalDate;


//<<< EDA / CQRS
@Entity
@Table(name="DashBoard_table")
@Data
public class DashBoard {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private Long postId;
        private String postTitle;
        private String postContent;
        private String postNickname;
        private List<String> commentList;


}
