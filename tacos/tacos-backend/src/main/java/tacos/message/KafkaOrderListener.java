package tacos.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tacos.entity.Order;

import static tacos.message.KafkaConfig.ORDER_TOPIC;

/**
 * @author Sui
 * @date 2019.04.23 15:09
 */
@Slf4j
@Component
public class KafkaOrderListener {

    /**
     * 监听消息
     * @param consumerRecord
     * @param order
     */
    @KafkaListener(topics = ORDER_TOPIC)
    public void receiveOrder(
            //ConsumerRecord对象除了Order对象, 还包含了topic, partition等信息
            ConsumerRecord<String, Order> consumerRecord) {
        log.info("Received from partition {} with timestamp {}",
                consumerRecord.partition(), consumerRecord.timestamp());
        log.info("{} : {}", consumerRecord.key(), consumerRecord.value());
    }
}
