package blog.domain;

<<<<<<< HEAD
import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
=======
import blog.infra.AbstractEvent;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
@ToString
public class PostEdited extends AbstractEvent {

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

    public PostEdited(Post aggregate) {
        super(aggregate);
    }

    public PostEdited() {
        super();
    }
}
<<<<<<< HEAD
//>>> DDD / Domain Event
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
