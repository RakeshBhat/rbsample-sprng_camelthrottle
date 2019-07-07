package com.rbsample.throttle.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rbsample.throttle.demo.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
