package tacos.message.amqp;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sui
 * @date 2019.04.23 11:11
 */
@Configuration
public class RabbitConfig {

    public static final String ORDER_RABBIT_QUEUE = "tacocloud.order.queue";

    /**
     * 配置消息转换器, 注入到rabbit template
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
