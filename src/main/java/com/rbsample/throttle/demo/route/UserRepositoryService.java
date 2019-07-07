package com.rbsample.throttle.demo.route;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbsample.throttle.demo.domain.User;
import com.rbsample.throttle.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRepositoryService {

	private UserRepository userRepository;

	@Autowired
	public UserRepositoryService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@Handler
	public User createUser(@Body User user){
		User usr = userRepository.save(user);
		log.info("Created User {} " , usr);
		return usr;
	}
	
	@Handler
	public Optional<User> getUserById(@Header(value = "userId") long userId){
		log.info("Search User by userId {} ", userId);
		return this.findUserById(userId);
	}
	
	@Handler
	public List<User> getUsers(){
		log.info("Search Users");
		return StreamSupport.stream(this.findAll().spliterator(), false).collect(Collectors.toList());
	}
	
	@Handler
	public void deleteUserById(@Header(value = "userId") long userId){
		log.info("Delete User by Id {} ", userId);
		this.deleteById(userId);
	}
	
	public Optional<User> findUserById(Long userId){
		Optional<User> user = userRepository.findById(userId);
		log.info("Searched User {} " , user);
		return user;
	}
	
	public Iterable<User> findAll(){
		return userRepository.findAll();
	}
	
	public void deleteById(Long userId){
		userRepository.deleteById(userId);
	}
	
}
