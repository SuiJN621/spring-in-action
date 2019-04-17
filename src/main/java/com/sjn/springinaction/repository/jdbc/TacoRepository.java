package com.sjn.springinaction.repository.jdbc;

import com.sjn.springinaction.entity.Taco;

/**
 * @author Sui
 * @date 2019.04.16 15:00
 */
public interface TacoRepository {

    Taco save(Taco taco);
}
