package tacos.message.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import tacos.entity.Order;

/**
 * @author Sui
 * @date 2019.04.23 9:34
 */
@Component
public class JmsSender {

    private Destination orderDestination;
    private JmsTemplate jmsTemplate;
    @Autowired
    public JmsSender(Destination orderDestination, JmsTemplate jmsTemplate) {
        this.orderDestination = orderDestination;
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * 传入消息转换器发送消息
     * @param order
     */
    public void sendOrderWithMC(Order order){
        jmsTemplate.send(orderDestination, session -> {
            ObjectMessage message = session.createObjectMessage(order);
            setSourceProperty(message);
            return message;
        });
    }

    /**
     * 使用默认消息转换器发送
     * @param order
     */
    public void sendOrderWithDefaultMC(Order order){
        jmsTemplate.convertAndSend(orderDestination, order);
    }

    /**
     * 使用默认消息转换器发送, 使用消息预处理器添加参数
     * @param order
     */
    public void sendOrderWithDefaultMCAndPostHandler(Order order){
        jmsTemplate.convertAndSend(orderDestination, order,
                message -> {
                    setSourceProperty(message);
                    return message;
                });
    }

    /**
     * 设置自定义消息头
     * @param message
     * @throws JMSException
     */
    private void setSourceProperty(Message message) throws JMSException {
        message.setStringProperty("X_ORDER_SOURCE", "WEB");
    }
}
