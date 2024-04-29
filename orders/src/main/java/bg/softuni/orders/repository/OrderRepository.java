package bg.softuni.orders.repository;

import bg.softuni.orders.model.OrderDTO;
import bg.softuni.orders.web.ObjectNotFoundException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

  private Map<String, OrderDTO> orders = new ConcurrentHashMap<>();

  public OrderDTO createOrder(OrderDTO orderDTO) {
    return orders.put(orderDTO.orderId() != null ?
        orderDTO.orderId() :
        UUID.randomUUID().toString(), orderDTO
    );
  }

  public Optional<OrderDTO> findOrder(String orderId) {
    return Optional.ofNullable(orders.get(orderId));
  }
}
