package tacos.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tacos.entity.Order;
import tacos.entity.User;

/**
 * @author Sui
 * @date 2019.04.17 14:49
 */
@Repository
public interface JpaOrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
