package tacos.repository.jdbc;

import tacos.entity.Order;

/**
 * @author Sui
 * @date 2019.04.16 15:01
 */
public interface OrderRepository {

    Order save(Order order);
}
