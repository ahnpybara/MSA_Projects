package blog.domain;

import blog.domain.*;
import blog.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
public class UserUpdated extends AbstractEvent {

    private Long id;
    private String password;
    private String name;
    private String nickname;
    private String email;
}
