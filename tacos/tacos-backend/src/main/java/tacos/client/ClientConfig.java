package tacos.client;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sui
 * @date 2019.04.22 14:47
 */
@Configuration
public class ClientConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
