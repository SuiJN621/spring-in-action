package tacos.message.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import tacos.entity.Order;

/**
 * @author Sui
 * @date 2019.04.23 9:57
 */
@Component
public class JmsListenerReceiver {

    /**
     * 监听消息推送(push模式)
     * @param order
     */
    @JmsListener(destination = JmsConfig.ORDER_STRING_DESTINATION)
    public void passiveReceive(Order order){
        //do something with order
    }
}
