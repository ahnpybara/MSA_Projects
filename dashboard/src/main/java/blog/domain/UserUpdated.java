package blog.domain;

import blog.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserUpdated extends AbstractEvent {

    private Long id;
    private String password;
    private String name;
    private String nickname;
}
