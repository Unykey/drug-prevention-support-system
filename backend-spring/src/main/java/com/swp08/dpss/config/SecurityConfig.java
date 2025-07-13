package com.swp08.dpss.config;

import com.swp08.dpss.exception.CustomAccessDeniedHandler;
import com.swp08.dpss.repository.UserRepository;
import com.swp08.dpss.security.jwt.JwtRequestFilter;
import com.swp08.dpss.service.impls.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerJwtAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    private static final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "api/user/profile"
    };

    private static final String[] PUBLIC_USER_URLS = {
            "/api/user/search",
            "/api/user/search/{id}",
            "api/user/delete/{id}"
    };

    private static final String[] ADMIN_URLS = {
            "/api/user/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF (common for stateless JWT-based APIs) (recommended for stateless APIs like JWT-based ones)
        http
                // Disable CSRF since JWT is stateless
                .csrf(AbstractHttpConfigurer::disable)

                // Allow CORS if needed (optional, especially when frontend is separate)
                .cors(Customizer.withDefaults())

                // Authorize endpoints
                // Configure authorization rules using the lambda DSL
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers(HttpMethod.GET, PUBLIC_USER_URLS).hasAnyRole("STAFF", "MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, PUBLIC_USER_URLS).hasAnyRole("STAFF", "MANAGER", "ADMIN")
                                .requestMatchers(ADMIN_URLS).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .exceptionHandling(e->e.accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )

                // Configure session management to be stateless
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Add your custom JWT filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtRequestFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}