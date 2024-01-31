package blog.auth;

import lombok.Data;

@Data
public class authUser {
    private String id;
    private String email;

    public authUser(String id, String email) {
        this.id = id;
        this.email = email;
    }
}
