package com.future.study.spring.large.scale.solution;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dexterleslie.Chan
 */
public interface UserRepository extends JpaRepository<User,Integer> {
}
