package tacos;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;
import tacos.entity.Order;
import tacos.entity.Taco;
import tacos.entity.User;
import tacos.message.KafkaOrderListener;
import tacos.message.KafkaOrderMessageService;
import tacos.repository.jpa.JpaOrderRepository;
import tacos.repository.jpa.JpaTacoRepository;
import tacos.repository.jpa.UserRepository;

/**
 * @author Sui
 * @date 2019.04.23 15:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {

    @Autowired
    private KafkaOrderMessageService kafkaOrderMessageService;

    @Autowired
    private KafkaOrderListener kafkaOrderListener;

    @Autowired
    private JpaOrderRepository orderRepository;

    @Autowired
    private JpaTacoRepository tacoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSendAndReceive(){
        Iterable<Taco> tacos = tacoRepository.findAll();
        Iterable<User> users = userRepository.findAll();

        ArrayList<Taco> tacoList = new ArrayList<>();
        tacos.forEach(tacoList::add);

        Order order = new Order();
        order.setDeliveryName("SuiJN");
        order.setDeliveryStreet("GuangDong Road");
        order.setDeliveryCity("ShangHai");
        order.setDeliveryState("ShangHai");
        order.setDeliveryZip("200001");
        order.setCcNumber("111111111111");
        order.setCcExpiration("11/11");
        order.setCcCVV("111");
        order.setUser(users.iterator().next());
        order.setTacos(tacoList);
        order = orderRepository.save(order);

        ListenableFuture<SendResult<String, Order>> result = kafkaOrderMessageService.sendOrder
                (order);
        result.addCallback(new ListenableFutureCallback<SendResult<String, Order>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("------------------------------");
                log.error("发送失败", ex);
                System.out.println("------------------------------");
            }
            @Override
            public void onSuccess(SendResult<String, Order> result) {
                System.out.println("------------------------------");
                log.info("发送成功: {}", result);
                System.out.println("------------------------------");
            }
        });

    }
}
