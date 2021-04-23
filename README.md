[![Codacy Badge](https://app.codacy.com/project/badge/Grade/ddae95adbea54bed8afcc56f40b906ff)](https://www.codacy.com/gh/igar15/restaurant-voting-system/dashboard)
[![Build Status](https://api.travis-ci.com/igar15/restaurant-voting-system.svg?branch=master)](https://travis-ci.com//igar15/restaurant-voting-system)

Restaurant Voting System Project 
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
* If user is not authorized, he can only view restaurants and their menus.  
* User must be authorized to work with his profile and vote for the restaurant.  

### <a href="domain_model.md">Application domain model schema</a>

### <a href="interaction_example.md">The schematic example of interaction between frontend and backend</a>

### API documentation:
#### Users
- POST /rest/profile/register (register a new user)
- GET /rest/profile (get user profile)
- PUT /rest/profile (update user profile)
- DELETE /rest/profile (delete user profile)
#### Restaurants
- POST /rest/restaurants (create a new restaurant)
- GET /rest/restaurants (get list of restaurants)
- GET /restaurants/{restaurantId} (get restaurant with id = restaurantId)
- PUT /rest/restaurants/{restaurantId} (update restaurant with id = restaurantId)
- DELETE /rest/restaurants/{restaurantId} (delete restaurant with id = restaurantId)
#### Menus
- POST /rest/restaurants/{restaurantId}/menus/today (create today's menu for restaurant with id = restaurantId)
- GET /rest/restaurants/{restaurantId}/menus/today (get today's menu for restaurant with id = restaurantId)
- PUT /rest/restaurants/{restaurantId}/menus/today (update today's menu for restaurant with id = restaurantId)
- DELETE /rest/restaurants/{restaurantId}/menus/today (delete today's menu for restaurant with id = restaurantId)
#### Votes
- POST /rest/votes?restaurantId={restaurantId} (register vote from user for restaurant with id = restaurantId)

### Curl commands to test API:
#### register a new User
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/votingsystem/rest/profile/register`
#### get User profile
`curl -s http://localhost:8080/votingsystem/rest/profile --user test@mail.ru:test-password`
#### update User profile
`curl -s -X PUT -d '{"name":"Updated User","email":"updated@mail.ru","password":"updated-password"}' -H 'Content-Type: application/json' http://localhost:8080/votingsystem/rest/profile --user test@mail.ru:test-password`
#### delete User profile
`curl -s -X DELETE http://localhost:8080/votingsystem/rest/profile --user updated@mail.ru:updated-password`
#### create a new Restaurant
`curl -s -i -X POST -d '{"name":"New Restaurant","address":"New address"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/votingsystem/rest/restaurants --user admin@gmail.com:admin`
#### get all Restaurants 
`curl -s http://localhost:8080/votingsystem/rest/restaurants`
#### get Restaurant with id=100002
`curl -s http://localhost:8080/votingsystem/rest/restaurants/100002`
#### update Restaurant with id=100002
`curl -s -X PUT -d '{"name":"Updated Restaurant","address":"Updated address"}' -H 'Content-Type: application/json' http://localhost:8080/votingsystem/rest/restaurants/100002 --user admin@gmail.com:admin`
#### delete Restaurant with id=100002
`curl -s -X DELETE http://localhost:8080/votingsystem/rest/restaurants/100003 --user admin@gmail.com:admin`
#### create today's Menu for Restaurant with id=100003
`curl -s -i -X POST -d '{"dishes":[{"name": "Dish1","price":200},{"name": "Dish2","price":300}]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/votingsystem/rest/restaurants/100003/menus/today --user admin@gmail.com:admin`
#### get today's Menu for Restaurant with id=100003
`curl -s http://localhost:8080/votingsystem/rest/restaurants/100003/menus/today`
#### update today's Menu for Restaurant with id=100003
`curl -s -X PUT -d '{"dishes":[{"name": "Dish1 Updated","price":250},{"name": "Dish2 Updated","price":350}]}' -H 'Content-Type: application/json' http://localhost:8080/votingsystem/rest/restaurants/100003/menus/today --user admin@gmail.com:admin`
#### delete today's Menu for Restaurant with id=100003
`curl -s -X DELETE http://localhost:8080/votingsystem/rest/restaurants/100003/menus/today --user admin@gmail.com:admin`
#### vote for today's menu of Restaurant with id=100003
`curl -s -i -X POST -d 'restaurantId=100003' http://localhost:8080/votingsystem/rest/votes --user user@yandex.ru:password`

