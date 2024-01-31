package blog.auth;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${your.jwt.secret}") // application.yml 등에서 읽어오도록 설정
    private String jwtSecret;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // CSRF 비활성화
        http.csrf().disable();

        // 세션 사용 안 함
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // formLogin 비활성화
                .formLogin().disable()
                // httpBasic 비활성화
                .httpBasic().disable()
                // 인증 실패 핸들러 설정
                .addFilterBefore(new JwtAuthorizationFilter(jwtSecret), UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                // 권한 없음 핸들러 설정
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .authorizeRequests()
                .anyRequest().permitAll();
    }

    // 인증 실패 핸들러 설정
    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "사용할 권한이 없습니다.");
        };
    }

    // 권한 없음 핸들러 설정
    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("권한이 없다.");
        };
    }
}