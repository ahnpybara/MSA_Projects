package blog.domain;

import blog.NotificationApplication;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Notify_table")
@Data
//<<< DDD / Aggregate Root
public class Notify {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long postId;

    private Long commentId;

    private String message;

    private String nickname;

    public static NotifyRepository repository() {
        NotifyRepository notifyRepository = NotificationApplication.applicationContext.getBean(
            NotifyRepository.class
        );
        return notifyRepository;
    }

    //<<< Clean Arch / Port Method
    public static void commentCreateAlert(CommentCreated commentCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Notify notify = new Notify();
        repository().save(notify);

        CommentCreateAlerted commentCreateAlerted = new CommentCreateAlerted(notify);
        commentCreateAlerted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(commentCreated.get???()).ifPresent(notify->{
            
            notify // do something
            repository().save(notify);

            CommentCreateAlerted commentCreateAlerted = new CommentCreateAlerted(notify);
            commentCreateAlerted.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
