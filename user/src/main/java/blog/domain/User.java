package blog.domain;

import blog.UserApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

     private String roles; //USER, ADMIN

    public List<String> getRoleList(){
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    //Controller에서 user를 불러올 때 영속성을 유지하기 위한 메소드
    //방법은 있겠지만 시간이 없는 관계로 이렇게 구현하겠습니다.
    //이 메소드 없으니 에러 납니다
    public void register(UserCreated userCreated){
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        String hassPassword = passwordEncoder.encode(userCreated.getPassword());
        validateDuplicate(userCreated.getEmail());
        setPassword(hassPassword);
        setEmail(userCreated.getEmail());
        setName(userCreated.getName());
        setNickname(userCreated.getNickname());
        if(userCreated.getEmail().equals("admin")){
            setRoles("ROLE_USER, ROLE_ADMIN");
        } else {
            setRoles("ROLE_USER");

        }
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

     //회원중복 확인
     public void validateDuplicate(String email){
        User user =repository().findByEmail(email);
        if(user!=null){
           throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
   }

}
//>>> DDD / Aggregate Root