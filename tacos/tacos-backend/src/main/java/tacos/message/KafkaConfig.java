package tacos.message;

import org.springframework.context.annotation.Configuration;

/**
 * @author Sui
 * @date 2019.04.23 15:11
 */
@Configuration
public class KafkaConfig {

    public static final String ORDER_TOPIC = "tacocloud.orders.topic";
}
