package blog.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUser {
    // 조회하고 싶은 필드값 넣기
    private Long id;
    private String email;
    private String nickName;
}