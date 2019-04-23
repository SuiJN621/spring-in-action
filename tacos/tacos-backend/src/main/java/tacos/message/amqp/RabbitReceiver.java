package tacos.message.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import tacos.entity.Order;

import static tacos.message.amqp.RabbitConfig.ORDER_RABBIT_QUEUE;

/**
 * @author Sui
 * @date 2019.04.23 14:01
 */
@Component
public class RabbitReceiver {

    private RabbitTemplate rabbitTemplate;
    private MessageConverter messageConverter;
    @Autowired
    public RabbitReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = rabbitTemplate.getMessageConverter();
    }

    /**
     * 接受消息, 返回Message
     * @return
     */
    public Order receive() {
        //接受消息只需要确定queue
        //receive方法会立即返回, 如果没有消息返回null
        //设置等待时间, 超时没有消息返回null, 配置文件属性spring.rabbitmq.template.receive-timeout
        Message message = rabbitTemplate.receive(ORDER_RABBIT_QUEUE, 30000);
        return message != null
                ? (Order) messageConverter.fromMessage(message)
                : null;
    }

    /**
     * 接收消息并强制转换为对象
     * @return
     */
    public Order receiveAndConvert() {
        return (Order) rabbitTemplate.receiveAndConvert(ORDER_RABBIT_QUEUE);
    }

    /**
     * 接收消息根据类型转换对象(至于Jackson2JsonMessageConverter满足这种用法)
     * @return
     */
    public Order receiveOrder() {
        return rabbitTemplate.receiveAndConvert(ORDER_RABBIT_QUEUE,
                new ParameterizedTypeReference<Order>() {});
    }
}
