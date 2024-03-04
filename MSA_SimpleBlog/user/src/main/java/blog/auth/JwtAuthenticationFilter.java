package blog.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import blog.domain.User;
import lombok.RequiredArgsConstructor;

//security /login에서 로그인을 요청하면  
//usernamePasswordAuthenticationFilter 동작을 함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter :로그인 시도중");
        // 1. username, password 받기
        try {
            // BufferedReader br = request.getReader();

            // String input =null;
            // while((input = br.readLine())!= null) {
            // System.out.println(input);
            // }

            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword());
            // loadUserByUsername()이 실행
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // authentication 객체가 session 영역에 저장됨 => 로그인이 되었다는 것
            ServiceUserDetail serviceUserDetail = (ServiceUserDetail) authentication.getPrincipal();
            System.out.println(serviceUserDetail.getUser().getEmail());
            // authentication객체가 session 영역에 저장을 하는 방법은 return을 해주면 됨
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하게 코딩할 수 있음
            // jwt 토큰을 사용하면서 세션을 만들 이유가 없지만, 권한 처리 때문에 session에 넣어줌

            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
        // security의 loadUserByUsername이 호출
        // userDetails를 세션에 담고
        // jwt토큰을 만들어서 응답
        return null;
    }

    // attemptAuthentication실행 후 인증이 정상적으로 되었으면 아래의 함수가 실행
    // JWT 토큰을 여기서 만들어서 request 요청한 사용자에게 JWT토큰을 response해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        System.out.println("인증이 무사히 성공됨");

        ServiceUserDetail serviceUserDetail = (ServiceUserDetail) authResult.getPrincipal();
        // Hash 암호 방식

        // 토큰이 짧아야 탈취가 되어도 나름 안전
        // 60000은 1분 * 10 = 10분 간 유지
        // 즉 현재시간 기준 10분간 유지
        // HMAC특징은 시크릿 값을 가지고 있어야함
        // claim 토큰에 담을 값
        String jwtToken = JWT.create()
                .withSubject("COS토큰")// 토큰 이름
                .withExpiresAt(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES))) // 토큰 만료 시간
                .withClaim("id", serviceUserDetail.getUser().getId())
                .withClaim("email", serviceUserDetail.getUser().getEmail())
                .withClaim("nickname", serviceUserDetail.getUser().getNickname())
                .withClaim("roles", serviceUserDetail.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(role -> role.trim()) // 각 권한 문자열에서 앞뒤 공백 제거

                        .collect(Collectors.toList())) // 권한 정보 추가
                // 시크릿 값 설정
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        // 쿠키에 토큰 추가
        Cookie cookie = new Cookie("msa", JwtProperties.TOKEN_PREFIX + jwtToken);
        cookie.setMaxAge((int) TimeUnit.MINUTES.toSeconds(550));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        System.out.println(cookie);
        response.addCookie(cookie);
        // response.addHeader(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX+jwtToken);
        // 유저네임, 패스워드가 정상이면 jwt 토큰을 생성하고
        // 클라이언트 쪽으로 JWT토큰을 응답
        // 요청할 때마다 JWT토큰을 가지고 요청하며
        // 서버는 JWT토큰이 유효한지를 판단(필터를 만들어야함)
        System.out.println(jwtToken);

        // 응답 본문에 데이터 추가
        Long userId = serviceUserDetail.getUser().getId();
        String nickname = serviceUserDetail.getUser().getNickname(); // 닉네임 가져오기

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"userId\": \"" + userId + "\", \"nickname\": \"" + nickname + "\"}");
    }
}