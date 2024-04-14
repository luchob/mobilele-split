package bg.softuni.mobilele.client.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  public Optional<UserDetails> getCurrentUser() {
    Authentication authentication = SecurityContextHolder
        .getContextHolderStrategy()
        .getContext()
        .getAuthentication();

    if (authentication != null &&
        authentication.isAuthenticated() &&
        authentication.getPrincipal() instanceof UserDetails ud) {
      return Optional.of(ud);
    }

    LOGGER.warn("No authenticated user!");

    return Optional.empty();
  }
}
