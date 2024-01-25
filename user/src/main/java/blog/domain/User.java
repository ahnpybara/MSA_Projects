package blog.domain;

import blog.UserApplication;
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

    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    // 안치윤 : DB에 유저 정보 생성시, 유저 생성됨 이벤트를 발행하는 메서드를 호출합니다
    @PostPersist
    public void onPostPersist() {
        UserCreated userCreated = new UserCreated(this);
        userCreated.publishAfterCommit();
    }

    // 안치윤 : DB에 유저 정보가 수정되기 전에, 유저 수정됨 이벤트를 발행하는 메서드를 호출합니다
    @PreUpdate
    public void onPreUpdate() {
        UserUpdated userUpdated = new UserUpdated(this);
        userUpdated.publishAfterCommit();
    }
}