[![Codacy Badge](https://app.codacy.com/project/badge/Grade/ddae95adbea54bed8afcc56f40b906ff)](https://www.codacy.com/gh/igar15/votingsystem/dashboard)
[![Build Status](https://api.travis-ci.com/igar15/votingsystem.svg?branch=master)](https://travis-ci.com//igar15/votingsystem)

Restaurant Voting System Project 
=================================

This is the REST API implementation of voting system for deciding where to have lunch.

### Technology stack used: 
* Maven
* Spring MVC
* Spring Data JPA (Hibernate)
* Spring Security
* Ehcache
* REST (Jackson)
* JUnit 5
* OpenAPI 2

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

###Application Domain Model Schema
![App Domain Model](https://user-images.githubusercontent.com/60218699/125639808-d707b978-3737-4a7b-be97-b3c6b28a7b81.png)

### API documentation:
#### Swagger documentation
- (/v2/api-docs)
- (/swagger-ui.html)
#### Users
- POST /rest/profile/register (register a new user)
- GET /rest/profile (get user profile)
- PUT /rest/profile (update user profile)
- DELETE /rest/profile (delete user profile)
#### Restaurants
- POST /rest/restaurants (create a new restaurant)
- GET /rest/restaurants (get list of restaurants)
- GET /rest/restaurants/{restaurantId} (get restaurant with id = restaurantId)
- PUT /rest/restaurants/{restaurantId} (update restaurant with id = restaurantId)
- DELETE /rest/restaurants/{restaurantId} (delete restaurant with id = restaurantId)
#### Menus
- POST /rest/restaurants/{restaurantId}/menus/today (create today's menu for restaurant with id = restaurantId)
- GET /rest/restaurants/{restaurantId}/menus/today (get today's menu for restaurant with id = restaurantId)
- PUT /rest/restaurants/{restaurantId}/menus/today (update today's menu for restaurant with id = restaurantId)
- DELETE /rest/restaurants/{restaurantId}/menus/today (delete today's menu for restaurant with id = restaurantId)
#### Votes
- POST /rest/votes?restaurantId={restaurantId} (register vote from authorized user for restaurant with id = restaurantId)

### Caching strategy
Spring caching (Ehcache provider):
- Get all restaurants (singleNonExpiryCache, evicts when create/update/delete any restaurant)
- Get today's menu for a restaurant (multiExpiryCache, cache key = {restaurantId} + currentDate, evicts by key, when create/update/delete today's menu for the restaurant, and when the restaurant deletes)  

Hibernate second level cache:
- Restaurant entity (CacheConcurrencyStrategy: NONSTRICT_READ_WRITE)