package blog.domain;

import blog.infra.AbstractEvent;
<<<<<<< HEAD
import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
=======
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
public class PostEdited extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
<<<<<<< HEAD
    private List<Long> commentList;
=======
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
    private String nickname;
}
