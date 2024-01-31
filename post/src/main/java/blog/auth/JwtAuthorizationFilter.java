package blog.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${your.jwt.secret}") // application.yml 등에서 읽어오도록 설정
    private final String jwtSecret; // JWT 시크릿 키

    public JwtAuthorizationFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaims(Map<String, Claim> claims) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (claims.containsKey("roles")) {
            try {
                String[] rolesArray = claims.get("roles").asArray(String.class);
                for (String role : rolesArray) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }
            } catch (UnsupportedOperationException e) {
                // asArray 메서드가 지원되지 않을 경우 단일 값으로 처리
                String role = claims.get("roles").asString();
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }

        return authorities;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 항상 실행되도록 조건을 true로 설정
        if (true) {
            // 쿠키 추출
            Cookie[] cookies = request.getCookies();
            String jwtToken = null;

            if (cookies != null) {
                // 쿠키에서 JWT 토큰 추출
                for (Cookie cookie : cookies) {
                    if ("msa".equals(cookie.getName())) {
                        jwtToken = cookie.getValue();
                        System.out.println("+++++++++++++++" + cookie.getName());
                        break;
                    }
                }
            }

            // JWT 토큰 검증 및 권한 추출
            if (jwtToken != null) {
                try {
                    // "Bearer " 이후의 부분만 추출
                    String actualToken = jwtToken.substring("Bearer".length());

                    System.out.println("Actual Token: " + actualToken);

                    // JWT 토큰 검증 및 클레임 추출
                    Map<String, Claim> claims = JWT.require(Algorithm.HMAC512(jwtSecret))
                            .build()
                            .verify(actualToken)
                            .getClaims();
                    // 정수일 경우 string으로 바꾸기

                    // AuthUser를 사용하고 싶으면 이렇게 하기
                    AuthUser userDetails = new AuthUser(
                            // id는 정수이기에 int로 뽑고 문자열로 변환
                            Long.valueOf(claims.get("id").asInt()),
                            claims.get("email").asString(),
                            claims.get("nickname").asString());

                    // 권한 추출
                    List<SimpleGrantedAuthority> authorities = extractAuthoritiesFromClaims(claims);

                    System.out
                            .println("=============================================================Claims: " + claims);

                    // Authentication 객체 생성 및 설정
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    System.out.println("=========================Authentication: " + authentication);

                } catch (Exception e) {
                    System.out.println("여기서 문제가 발생");
                }
            }

        }

        filterChain.doFilter(request, response);
    }

}