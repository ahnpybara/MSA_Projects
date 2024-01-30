package blog.domain;

import blog.infra.AbstractEvent;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
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
