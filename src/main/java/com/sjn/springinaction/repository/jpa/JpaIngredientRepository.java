package com.sjn.springinaction.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjn.springinaction.entity.Ingredient;

/**
 * @author Sui
 * @date 2019.04.17 14:47
 */
@Repository
public interface JpaIngredientRepository extends CrudRepository<Ingredient, String> {
}
