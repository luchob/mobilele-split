package bg.softuni.mobilele.client.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(
            authorizeHttpRequests ->
                authorizeHttpRequests.
                    requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                    requestMatchers("/", "/users/login", "/users/login-error").permitAll().
                    anyRequest().authenticated()
        )
        .formLogin(
            (formLogin) ->
                formLogin.
                    loginPage("/users/login").
                    usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                    passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                    defaultSuccessUrl("/").
                    failureForwardUrl("/users/login-error")
        )
        .logout((logout) ->
            logout.logoutUrl("/users/logout").
                logoutSuccessUrl("/").//go to homepage after logout
                invalidateHttpSession(true)
        );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

    UserDetails user = User.builder()
        .username("user@example.com")
        .password(passwordEncoder.encode("topsecret"))
        .authorities("ADMIN", "USER")
        .build();

    return new InMemoryUserDetailsManager(user);
  }
}
