#Description
- Yet another sample to help with spring-boot, spring-web and apache camel. Was trying to find a simple solution to implement 
request limiter. Didn't find a out-of-box solution, so built a solution with no additional dependencies.
- In this sample, I'm using spring web to provide REST API access and apache camel to integrate calls to database (in-memory).
- To limit the excess end-point calls or rate limiter, using apache camel throttler, it checks for request count and time elapsed.   