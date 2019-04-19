package tacos.repository.jdbc;

import tacos.entity.Taco;

/**
 * @author Sui
 * @date 2019.04.16 15:00
 */
public interface TacoRepository {

    Taco save(Taco taco);
}
