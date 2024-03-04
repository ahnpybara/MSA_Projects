package blog.auth;

public interface JwtProperties {
    String SECRET ="cos"; //비밀값
    int EXPIRATION_TIME = 60000*10; //10분
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
}