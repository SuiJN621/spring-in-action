package com.sjn.springinaction.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjn.springinaction.entity.Order;

/**
 * @author Sui
 * @date 2019.04.17 14:49
 */
@Repository
public interface JpaOrderRepository extends CrudRepository<Order, Long> {
}
