package blog.domain;

<<<<<<< HEAD
import blog.domain.*;
import blog.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
=======
import blog.infra.AbstractEvent;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
>>>>>>> ccbb7ea0416aeb12de16d0890e37e1656e617730
@ToString
public class UserUpdated extends AbstractEvent {

    private Long id;
    private String password;
    private String name;
    private String nickname;
    private Long userId;
}
