package bg.softuni.orders.web;

import bg.softuni.orders.model.OrderDTO;
import bg.softuni.orders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
  private final OrderRepository orderRepository;

  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @PostMapping("/{orderId}")
  public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
    orderRepository.createOrder(orderDTO);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDTO> getOrder(@PathVariable(name = "orderId") String orderId) {

    var order = orderRepository.
        findOrder(orderId)
        .orElseThrow(() -> new ObjectNotFoundException("Not Found!"));

    return ResponseEntity.ok(order);
  }

}
