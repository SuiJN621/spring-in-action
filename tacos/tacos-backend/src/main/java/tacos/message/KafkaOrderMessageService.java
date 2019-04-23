package tacos.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import tacos.entity.Order;

import static tacos.message.KafkaConfig.ORDER_TOPIC;

/**
 * @author Sui
 * @date 2019.04.23 15:04
 */
@Service
public class KafkaOrderMessageService {

    private KafkaTemplate<String, Order> kafkaTemplate;

    @Autowired
    public KafkaOrderMessageService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送消息
     *
     * @param order
     */
    public ListenableFuture<SendResult<String, Order>> sendOrder(Order order) {
        //需要传入topic, payload
        //可以使用spring.kafka.template.default-topic设置默认主题 调用sendDefault()
        return kafkaTemplate.send(ORDER_TOPIC, order);
    }
}
