package blog.domain;

<<<<<<< HEAD
import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
=======
import blog.infra.AbstractEvent;
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
import lombok.*;

//<<< DDD / Domain Event
@Data
<<<<<<< HEAD
=======
@EqualsAndHashCode(callSuper=false)
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
@ToString
public class PostDeleted extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
<<<<<<< HEAD
    private List<Long> commentList;
    private String nickname;
=======
    private String nickname;
    private Long userId;
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730

    public PostDeleted(Post aggregate) {
        super(aggregate);
    }

    public PostDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
