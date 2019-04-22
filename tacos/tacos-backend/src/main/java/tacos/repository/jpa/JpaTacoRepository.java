package tacos.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tacos.entity.Taco;

/**
 * @author Sui
 * @date 2019.04.17 14:47
 */
@Repository
public interface JpaTacoRepository extends PagingAndSortingRepository<Taco, Long> {
}
