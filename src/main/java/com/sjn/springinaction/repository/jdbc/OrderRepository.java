package com.sjn.springinaction.repository.jdbc;

import com.sjn.springinaction.entity.Order;

/**
 * @author Sui
 * @date 2019.04.16 15:01
 */
public interface OrderRepository {

    Order save(Order order);
}
