package com.rbsample.throttle.demo.service;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rbsample.throttle.demo.Response;
import com.rbsample.throttle.demo.domain.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	private CamelContext camelContext;

	@Produce(uri = "direct:saveUserThrottle")
	private ProducerTemplate saveUserProducerTemplate;
	
	@Produce(uri = "direct:getUserByIdThrottle")
	private ProducerTemplate getUserByIdProducerTemplate;
	
	@Produce(uri = "direct:getUserThrottle")
	private ProducerTemplate getUserProducerTemplate;
	
	@Produce(uri = "direct:deleteUserByIdThrottle")
	private ProducerTemplate deleteUserByIdTemplate;
	
	@Autowired
	public UserService(CamelContext camelContext, ProducerTemplate saveUserProducerTemplate,
			ProducerTemplate getUserByIdProducerTemplate,
			ProducerTemplate getUserProducerTemplate,
			ProducerTemplate deleteUserByIdTemplate) {
		super();
		this.camelContext = camelContext;
		this.saveUserProducerTemplate = saveUserProducerTemplate;
		this.getUserByIdProducerTemplate = getUserProducerTemplate;
		this.getUserProducerTemplate = getUserProducerTemplate;
		this.deleteUserByIdTemplate =  deleteUserByIdTemplate;
	}
	
	public ResponseEntity<Response> createUser(User user) throws Exception{
		DefaultExchange exchange = new DefaultExchange(camelContext, ExchangePattern.InOut);
		DefaultMessage message = new DefaultMessage(camelContext);
				
		camelContext.startRoute("directsaveUserThrottle");
		saveUserProducerTemplate.start();
		
		message.setBody(user, User.class);
		exchange.setIn(message);

		Exchange outExchange =  saveUserProducerTemplate.send(exchange);
		String body = outExchange.getIn().getBody(String.class);
		
		log.info("Out Exchange data {}" , body);
		if("Error".equals(body)){
			new ResponseEntity<Response>(new Response("createUser", body), HttpStatus.TOO_MANY_REQUESTS);
		}
		new ResponseEntity<Response>(new Response("createUser", body), HttpStatus.CREATED);
		
		saveUserProducerTemplate.stop();
		
		return new ResponseEntity<Response>(new Response("createUser", body), HttpStatus.CREATED);
	}
	
	public ResponseEntity<String> updateUser(User user) throws Exception{
		DefaultExchange exchange = new DefaultExchange(camelContext, ExchangePattern.InOut);
		DefaultMessage message = new DefaultMessage(camelContext);
				
		camelContext.startRoute("directsaveUserThrottle");
		getUserByIdProducerTemplate.start();
		
		message.setBody(user, User.class);
		exchange.setIn(message);
		
		Exchange outExchange =  getUserByIdProducerTemplate.send(exchange);
		String body = outExchange.getIn().getBody(String.class);
		
		log.info("Out Exchange data {}" , body);
		if("Error".equals(body)){
			new ResponseEntity<Response>(new Response("createUser", body), HttpStatus.TOO_MANY_REQUESTS);
		}
				
		getUserByIdProducerTemplate.stop();

		return new ResponseEntity<String>("updated User", HttpStatus.OK);
	}
	
	public ResponseEntity<Response> deleteUserById(long userId) throws Exception{
		DefaultExchange exchange = new DefaultExchange(camelContext, ExchangePattern.InOut);
		DefaultMessage message = new DefaultMessage(camelContext);
				
		camelContext.startRoute("directdeleteUserByIdThrottle");
		deleteUserByIdTemplate.start();
		
		message.setHeader("userId", userId);
		exchange.setIn(message);
		
		exchange.setIn(message);
		
		Exchange outExchange =  deleteUserByIdTemplate.send(exchange);
		String body = outExchange.getIn().getBody(String.class);
		
		log.info("Out Exchange data {}" , body);
		if("Error".equals(body)){
			new ResponseEntity<Response>(new Response("getUser", body), HttpStatus.TOO_MANY_REQUESTS);
		}
				
		deleteUserByIdTemplate.stop();

		return new ResponseEntity<Response>(new Response("deleteUserById", body),  HttpStatus.OK);

	}
	
	public ResponseEntity<Response> getUserById(long userId) throws Exception{
		DefaultExchange exchange = new DefaultExchange(camelContext, ExchangePattern.InOut);
		DefaultMessage message = new DefaultMessage(camelContext);
				
		camelContext.startRoute("directgetUserByIdThrottle");
		getUserByIdProducerTemplate.start();
		
		message.setHeader("userId", userId);
		exchange.setIn(message);
		
		Exchange outExchange =  getUserByIdProducerTemplate.send(exchange);
		String body = outExchange.getIn().getBody(String.class);
		
		log.info("Out Exchange data {}" , body);
		if("Error".equals(body)){
			new ResponseEntity<Response>(new Response("getUserById", body), HttpStatus.TOO_MANY_REQUESTS);
		}
				
		getUserByIdProducerTemplate.stop();

		return new ResponseEntity<Response>(new Response("getUserById", body),  HttpStatus.OK);
	}
	
	public ResponseEntity<Response> getUsers() throws Exception{
		DefaultExchange exchange = new DefaultExchange(camelContext, ExchangePattern.InOut);
		DefaultMessage message = new DefaultMessage(camelContext);
				
		camelContext.startRoute("directgetUserThrottle");
		getUserProducerTemplate.start();
		
		exchange.setIn(message);
		
		Exchange outExchange =  getUserProducerTemplate.send(exchange);
		String body = outExchange.getIn().getBody(String.class);
		
		log.info("Out Exchange data {}" , body);
		if("Error".equals(body)){
			new ResponseEntity<Response>(new Response("getUser", body), HttpStatus.TOO_MANY_REQUESTS);
		}
				
		getUserProducerTemplate.stop();

		return new ResponseEntity<Response>(new Response("getUser", body),  HttpStatus.OK);
	}
}
