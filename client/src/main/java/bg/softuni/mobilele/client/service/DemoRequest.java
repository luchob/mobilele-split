package bg.softuni.mobilele.client.service;

import bg.softuni.mobilele.client.model.OrderDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DemoRequest implements CommandLineRunner {

  private final RestClient restClient;

  private final Logger LOGGER = LoggerFactory.getLogger(DemoRequest.class);

  public DemoRequest(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public void run(String... args) {

    List<OrderDTO> orderDTOList = restClient
        .get()
        .uri("/all")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});

    assert orderDTOList != null;

    orderDTOList.forEach(o -> LOGGER.info("Order: {}", o));
  }
}
