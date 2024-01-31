package blog.auth;

import lombok.Data;

@Data
public class authUser {
    private String id;
    private String email;
    private String nickname;

    public authUser(String id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}
