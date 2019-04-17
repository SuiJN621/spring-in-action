package com.sjn.springinaction.repository.jdbc;

import com.sjn.springinaction.entity.Ingredient;

/**
 * @author Sui
 * @date 2019.04.16 14:24
 */
public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findOne(String id);

    Ingredient save(Ingredient ingredient);
}
