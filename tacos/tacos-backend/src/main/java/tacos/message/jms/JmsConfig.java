package tacos.message.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import tacos.entity.Order;

/**
 * @author Sui
 * @date 2019.04.23 9:34
 */
@Configuration
public class JmsConfig {

    public static final String ORDER_STRING_DESTINATION = "com.sjn.order";

    /**
     * 注册新的MessageConverter代替默认的SimpleMessageConverter
     *
     * @return
     */
    @Bean
    public MappingJackson2MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        //设置typeId key值, 接收方根据value值决定将message转换为何种类型, 默认是类的全名
        //接收方也需要进行类似配置
        messageConverter.setTypeIdPropertyName("_typeId");

        //配置type mapping, 避免使用类全名
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("order", Order.class);
        messageConverter.setTypeIdMappings(typeIdMappings);

        return messageConverter;
    }

    @Bean
    public Destination orderDestination(){
        //也可以直接使用String定义目标, 不指定发送目标时需要配置默认目标spring.jms.template.default-destination
        return new ActiveMQQueue(ORDER_STRING_DESTINATION);
    }
}
