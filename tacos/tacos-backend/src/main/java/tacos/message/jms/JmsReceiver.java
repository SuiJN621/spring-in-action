package tacos.message.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import tacos.entity.Order;

/**
 * @author Sui
 * @date 2019.04.23 9:34
 */
@Component
public class JmsReceiver {

    private Destination orderDestination;
    private JmsTemplate jmsTemplate;
    private MessageConverter messageConverter;
    @Autowired
    public JmsReceiver(Destination orderDestination, JmsTemplate jmsTemplate, MessageConverter messageConverter) {
        this.orderDestination = orderDestination;
        this.jmsTemplate = jmsTemplate;
        this.messageConverter = messageConverter;
    }

    /**
     * 接收消息, 调用转换器转换
     * @return
     * @throws JMSException
     */
    public Order receiveOrder() throws JMSException {
        //receive方法会阻塞直到获取到message或超时
        Message message = jmsTemplate.receive(orderDestination);
        return (Order)messageConverter.fromMessage(message);
    }

    /**
     * 接受消息使用默认转换器(不需要注入)
     * @return
     * @throws JMSException
     */
    public Order receiveAndConvertOrder() throws JMSException {
        return (Order) jmsTemplate.receiveAndConvert(orderDestination);
    }
}
