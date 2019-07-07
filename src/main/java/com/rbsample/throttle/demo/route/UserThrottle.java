package com.rbsample.throttle.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.ThrottlerRejectedExecutionException;
import org.springframework.stereotype.Component;

@Component
public class UserThrottle extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		onException(ThrottlerRejectedExecutionException.class)
		.handled(true)
		.transform(simple("Error: Exceeded the max throttle rate of 1 within 7000ms"))
		.stop();
		
		from("direct:saveUserThrottle")
		.routeId("directsaveUserThrottle")
		.throttle(1)
		.timePeriodMillis(7000)
		.rejectExecution(true)
		.bean(UserRepositoryService.class, "createUser")
		.marshal().json(JsonLibrary.Jackson)
		.log("createUser: ${body}");
		
		from("direct:getUserByIdThrottle")
		.routeId("directgetUserByIdThrottle")
		.throttle(1)
		.timePeriodMillis(5000)
		.rejectExecution(true)
		.bean(UserRepositoryService.class, "getUserById")
		.marshal().json(JsonLibrary.Jackson)
		.log("getUserById : ${body}");
		
		from("direct:getUserThrottle")
		.routeId("directgetUserThrottle")
		.throttle(1)
		.timePeriodMillis(5000)
		.rejectExecution(true)
		.bean(UserRepositoryService.class, "getUsers")
		.marshal().json(JsonLibrary.Jackson)
		.log("getUsers : ${body}");
		
		from("direct:deleteUserByIdThrottle")
		.routeId("directdeleteUserByIdThrottle")
		.throttle(1)
		.timePeriodMillis(7000)
		.rejectExecution(true)
		.bean(UserRepositoryService.class, "deleteUserById")
		.log("Deleted User by Id")
		.transform(simple("Deleted User by Id"));
	}

}
