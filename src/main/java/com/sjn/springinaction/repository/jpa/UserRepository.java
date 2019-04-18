package com.sjn.springinaction.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sjn.springinaction.entity.User;

/**
 * @author Sui
 * @date 2019.04.17 16:56
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
