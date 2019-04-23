package tacos.message.amqp;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tacos.entity.Order;

import static tacos.message.amqp.RabbitConfig.ORDER_RABBIT_QUEUE;

/**
 * @author Sui
 * @date 2019.04.23 11:07
 */
@Component
public class RabbitSender {

    private RabbitTemplate rabbitTemplate;
    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送消息
     * @param order
     */
    public void sendOrder(Order order) {
        //获取配置的消息转换器
        MessageConverter converter = rabbitTemplate.getMessageConverter();
        //设置消息参数
        MessageProperties props = new MessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = converter.toMessage(order, props);
        //发送消息需要定义routing key, exchange key, 这两个参数决定消息被路由到哪个queue
        //缺省使用配置的默认值spring.rabbitmq.template.exchange和spring.rabbitmq.template.routing-key
        rabbitTemplate.send(ORDER_RABBIT_QUEUE, message);
    }

    public void sendOrderWithPostProcessor(Order order) {
        rabbitTemplate.convertAndSend(ORDER_RABBIT_QUEUE, order,
                //定义message post processor消息预处理器 添加参数
                message -> {
                    MessageProperties props = message.getMessageProperties();
                    props.setHeader("X_ORDER_SOURCE", "WEB");
                    return message;
                });
    }
}
