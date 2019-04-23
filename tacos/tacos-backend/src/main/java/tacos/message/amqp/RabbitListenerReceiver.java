package tacos.message.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import tacos.entity.Order;

import static tacos.message.amqp.RabbitConfig.ORDER_RABBIT_QUEUE;

/**
 * @author Sui
 * @date 2019.04.23 14:22
 */
@Component
public class RabbitListenerReceiver {

    @RabbitListener(queues = ORDER_RABBIT_QUEUE)
    public void receiveOrder(Order order) {
        //do something with order
    }
}
