package vivas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vivas.common.jwt.JWTAthenticationEntryPoint;
import vivas.common.jwt.JWTAuthenticationFilter;
import vivas.service.UserDetailsImplService;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTAthenticationEntryPoint point;
    @Autowired
    private JWTAuthenticationFilter filter;
    @Autowired
    private UserDetailsImplService jpaUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // configuration
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(
                        auth ->
                                //auth.requestMatchers("/home/**").authenticated()
                                //.requestMatchers("/api/v1/auth/login")
                                //.permitAll()
                                auth.requestMatchers("/api/web/auth/register"
                                                , "/api/web/auth/login"
                                                , "/api/web/auth/refreshtoken"
                                                , "/swagger-ui/**"
                                        , "/v3/api-docs/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                //.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(jpaUserDetailService);
        return http.build();
    }
}
