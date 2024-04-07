package bg.softuni.orders.orders;

import static java.util.stream.Collectors.joining;

import bg.softuni.orders.model.OrderDTO;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

  @GetMapping("/all")
  public List<OrderDTO> test(@AuthenticationPrincipal UserDetails user) {

    // DUMMY IMPLEMENTATION

    String allRoles = user
        .getAuthorities()
        .stream().map(Object::toString)
        .collect(joining(", "));

    String debug =  user.getUsername() + " [ " + allRoles + "]";

    LOGGER.info("User: {}", debug);

    return List.of(
        new OrderDTO(UUID.randomUUID().toString(), debug),
        new OrderDTO(UUID.randomUUID().toString(), debug)
    );
  }

}
