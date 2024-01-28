package blog.domain;

import blog.NotificationApplication;
<<<<<<< HEAD
import blog.domain.CommentCreateAlerted;
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
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

<<<<<<< HEAD
    @PostPersist
    public void onPostPersist() {
        CommentCreateAlerted commentCreateAlerted = new CommentCreateAlerted(
            this
        );
        commentCreateAlerted.publishAfterCommit();
    }

=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
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

<<<<<<< HEAD
        /** Example 2:  finding and process
=======
        /** Example 2:  finding and process1
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
        
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
