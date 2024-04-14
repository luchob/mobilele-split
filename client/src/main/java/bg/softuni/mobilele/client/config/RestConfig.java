package bg.softuni.mobilele.client.config;

import bg.softuni.mobilele.client.service.DemoRequest;
import bg.softuni.mobilele.client.service.JwtService;
import bg.softuni.mobilele.client.service.UserService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestConfig {

  private final Logger LOGGER = LoggerFactory.getLogger(RestConfig.class);

  @Bean
  public RestClient restClient(ClientHttpRequestInterceptor interceptor) {
    return RestClient.builder()
        .baseUrl("http://localhost:8080")
        .requestInterceptor(interceptor)
        .defaultHeaders(httpHeaders -> httpHeaders.set("Content-Type", "application/json"))
        .build();
  }

  @Bean
  public ClientHttpRequestInterceptor bearerToken(JwtService jwtService,
      UserService userService) {
    return (r, b, e) -> {

      userService
          .getCurrentUser()
          .ifPresentOrElse(ud -> {
                String token = jwtService.generateToken(ud.getUsername(), Map.of("roles",
                    ud.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()));
                r.getHeaders().setBearerAuth(token);
              },
              () -> LOGGER.error("No user for this REST request, please check."));

      // DUMMY DATA, should be replaced with actual user data of the session user

      return e.execute(r, b);
    };
  }

}
