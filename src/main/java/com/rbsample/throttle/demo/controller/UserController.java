package com.rbsample.throttle.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbsample.throttle.demo.Response;
import com.rbsample.throttle.demo.domain.User;
import com.rbsample.throttle.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {
	
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> createUser(@RequestBody User user) throws Exception{
		return userService.createUser(user);		
	}
	
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> getUserById(@PathVariable long userId) throws Exception{
		log.info("Searching user by user Id {}", userId);
		return userService.getUserById(userId);
	}
	
	@GetMapping()
	public ResponseEntity<Response> getUsers() throws Exception{
		return userService.getUsers();
	}
	
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUser(@RequestBody User user) throws Exception{
		return userService.updateUser(user);
	}
	
	@DeleteMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deleteUserById(@PathVariable long userId) throws Exception{
		return userService.deleteUserById(userId);
	}

}
