package bg.softuni.mobilele.client.service;

import bg.softuni.mobilele.client.model.OrderDTO;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OrderService {

  private final RestClient restClient;
  private final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

  public OrderService(RestClient restClient) {
    this.restClient = restClient;
  }

  public List<OrderDTO> getAllOrders() {

    List<OrderDTO> allOrders = restClient
        .get()
        .uri("/all")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});

    assert allOrders != null;

    LOGGER.info("Retrieved {} orders.", allOrders.size());

    return allOrders;
  }

  public Optional<OrderDTO> findOrderById(String orderId) {
    OrderDTO orderDTO = restClient
        .get()
        .uri("/orders/{orderId}", orderId)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(OrderDTO.class);

    return Optional.ofNullable(orderDTO);
  }

}
