package bg.softuni.mobilele.client.web;

import bg.softuni.mobilele.client.model.OrderDTO;
import bg.softuni.mobilele.client.service.OrderService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/orders/all")
  public String orders(Model model) {

    model.addAttribute("orders", orderService.getAllOrders());

  return "orders";
  }


}
