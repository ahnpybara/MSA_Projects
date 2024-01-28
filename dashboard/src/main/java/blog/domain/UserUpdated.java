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
public class UserUpdated extends AbstractEvent {

    private Long id;
    private String password;
    private String name;
    private String nickname;
}
