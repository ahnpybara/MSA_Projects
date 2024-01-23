package blog.domain;

import blog.UserApplication;
import blog.domain.UserCreated;
import blog.domain.UserUpdated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User_table")
@Data
//<<< DDD / Aggregate Root
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password;

    private String name;

    private String nickname;

    private String email;

    @PostPersist
    public void onPostPersist() {
        UserCreated userCreated = new UserCreated(this);
        userCreated.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() {
        UserUpdated userUpdated = new UserUpdated(this);
        userUpdated.publishAfterCommit();
    }

    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }
}
//>>> DDD / Aggregate Root
