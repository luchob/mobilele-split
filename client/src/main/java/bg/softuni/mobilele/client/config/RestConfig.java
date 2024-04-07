package bg.softuni.mobilele.client.config;

import bg.softuni.mobilele.client.service.JwtService;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestConfig {

  @Bean
  public RestClient restClient(ClientHttpRequestInterceptor interceptor) {
    return  RestClient.builder()
        .baseUrl("http://localhost:8080")
        .requestInterceptor(interceptor)
        .defaultHeaders(httpHeaders -> httpHeaders.set("Content-Type", "application/json"))
        .build();
  }

  @Bean
  public ClientHttpRequestInterceptor bearerToken(JwtService jwtService) {
    return (r, b, e) -> {
      // DUMMY DATA, should be replaced with actual user data of the session user
      String token = jwtService.generateToken("test@test.com", Map.of("roles",
          List.of("ADMIN", "USER")));
      r.getHeaders().setBearerAuth(token);
      return e.execute(r, b);
    };
  }

}
