[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7cbbfc11cdca4502899c50db977bbfe3)](https://www.codacy.com/gh/igar15/restaurant-voting-system/dashboard)
[![Build Status](https://api.travis-ci.com/igar15/restaurant-voting-system.svg?branch=master)](https://travis-ci.com//igar15/restaurant-voting-system)

Restaurants Voting System Project 
=================================

This is the REST API implementation of voting system for deciding where to have lunch.

### Technology stack used: 
* Maven
* Spring MVC
* Spring Security
* JPA(Hibernate)
* REST(Jackson)
* JUnit

### Project key logic:
* There are 2 types of users: admin and regular users.
* Admins can input a restaurant and it's lunch menu of the day.
* Menu changes each day (admins do the updates).
* Users can vote on which restaurant they want to have lunch at.
* Only one vote counted per user.
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
* Each restaurant provides a new menu each day.

### <a href="domain_model.md">Application domain model schema</a>

### <a href="interaction_example.md">The schematic example of interaction between frontend and backend</a>

### API documentation:
- GET /restaurants - get list of restaurants
- GET /restaurants/restaurantId - get a restaurant with id = restaurantId
- POST /restaurants/ - create a new restaurant
- PUT /restaurants/restaurantId - update a restaurant with id = restaurantId
- DELETE /restaurants/restaurantId - delete a restaurant with id = restaurantId
- ..............................

### Curl commands to test API:
- curl -v http://localhost:8082/rest-voting-system/restaurants
- ....
