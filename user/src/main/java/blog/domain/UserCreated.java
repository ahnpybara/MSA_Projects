package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class UserCreated extends AbstractEvent {

    private Long id;
    private String password;
    private String name;
    private String nickname;
    private String email;

    public UserCreated(User aggregate) {
        super(aggregate);
    }

    public UserCreated() {
        super();
    }
}
//>>> DDD / Domain Event
