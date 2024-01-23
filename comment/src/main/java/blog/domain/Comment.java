package blog.domain;

import blog.CommentApplication;
import blog.domain.AllCommentsDeleted;
import blog.domain.CommentCreated;
import blog.domain.CommentDeleted;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Comment_table")
@Data
//<<< DDD / Aggregate Root
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private Long postId;

    private String nickname;

    @PostPersist
    public void onPostPersist() {
        CommentCreated commentCreated = new CommentCreated(this);
        commentCreated.publishAfterCommit();

        AllCommentsDeleted allCommentsDeleted = new AllCommentsDeleted(this);
        allCommentsDeleted.publishAfterCommit();
    }

    @PreRemove
    public void onPreRemove() {
        CommentDeleted commentDeleted = new CommentDeleted(this);
        commentDeleted.publishAfterCommit();
    }

    public static CommentRepository repository() {
        CommentRepository commentRepository = CommentApplication.applicationContext.getBean(
            CommentRepository.class
        );
        return commentRepository;
    }

    //<<< Clean Arch / Port Method
    public static void deleteAllComments(PostDeleted postDeleted) {
        //implement business logic here:

        /** Example 1:  new item 
        Comment comment = new Comment();
        repository().save(comment);

        AllCommentsDeleted allCommentsDeleted = new AllCommentsDeleted(comment);
        allCommentsDeleted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(postDeleted.get???()).ifPresent(comment->{
            
            comment // do something
            repository().save(comment);

            AllCommentsDeleted allCommentsDeleted = new AllCommentsDeleted(comment);
            allCommentsDeleted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void updateUser(UserUpdated userUpdated) {
        //implement business logic here:

        /** Example 1:  new item 
        Comment comment = new Comment();
        repository().save(comment);

        */

        /** Example 2:  finding and process
        
        repository().findById(userUpdated.get???()).ifPresent(comment->{
            
            comment // do something
            repository().save(comment);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
