The schematic example of interaction between frontend and backend parts of application through REST API
=================================


#### 1. User visits the website  
When a user visits the website, the browser displays a page that contains a list of restaurants and today's lunch menu for the first restaurant on the list.    
To do this the frontend sends HTTP GET request to get a list of restaurants and HTTP GET request to get today's lunch menu for the first restaurant on the list.  
![0_user_view_app_open](https://user-images.githubusercontent.com/60218699/111033652-559ce700-8423-11eb-8ee3-e7197049c6b0.png)

#### 2. User selects a restaurant  
When a user selects a restaurant on the restaurants list, he will be presented today's lunch menu of that restaurant.  
To do this the frontend sends HTTP GET request to get today's lunch menu for the selected restaurant.  
If restaurant's today's lunch menu does not exist yet or its published status is false, a corresponding message will be displayed to the user.  
![1_user_view_menu_does_not_exist_or_not_published](https://user-images.githubusercontent.com/60218699/111033789-f9869280-8423-11eb-8db1-8bb13f734a43.png)    
Otherwise, the user will be presented today's lunch menu of the selected restaurant.  
If restaurant's today's lunch menu presented, and the user is logged in, he can vote for restaurant by clicking the button "Vote!".  
In this case frontend sends HTTP POST request to register user vote for the selected restaurant.  
![2_user_view_menu_exists_published](https://user-images.githubusercontent.com/60218699/111033721-af9dac80-8423-11eb-8194-899ad7606353.png)

#### 3. Admin selects a restaurant  
When an admin selects a restaurant on the restaurants list, he will be presented today's lunch menu of that restaurant.  
To do this the frontend sends HTTP GET request to get today's lunch menu for the selected restaurant.  
If restaurant's today's lunch menu does not exist yet, a corresponding message will be displayed to the admin.  
Admin can create today's lunch menu for the selected restaurant by clicking the button "Add Menu".  
In this case frontend sends HTTP POST request to create today's lunch menu for the selected restaurant.  
![3_admin_view_menu_does_not_exist](https://user-images.githubusercontent.com/60218699/111033833-28046d80-8424-11eb-9202-19af72516ae0.png)

#### 4. The menu has been created    
Once created, the menu is displayed on the page (no dishes have been added to the menu at the moment).   
The created menu published status is 'false', so the menu is not available for viewing by users.  
Since menu's published status is 'false', the page displays buttons for adding/updating/deleting dishes to the menu.  
![4_admin_view_menu_exists_not_published_dishes_do_not_exist](https://user-images.githubusercontent.com/60218699/111033853-394d7a00-8424-11eb-9668-c4c5a6b9d2af.png)

#### 5. Admin creates a dish for the menu  
When admin presses "Add Dish" or "Update" (on dish list) buttons, a form for creating/updating a dish appears.  
After the admin creates or updates a dish he presses the "Save" button to save changes.  
In this case frontend sends HTTP POST or PUT request to create or update a dish for the today's lunch menu for the selected restaurant.  
![5_admin_view_menu_exists_not_published_dish_create_form](https://user-images.githubusercontent.com/60218699/111033883-52eec180-8424-11eb-825d-30ed0bbb1b5b.png) 

#### 6. Admin publishes the menu  
After adding all dishes to the today's lunch menu of the selected restaurant, admin presses the "Publish Menu" button to publish the menu.  
In this case frontend sends HTTP PUT request to update published status to 'true' for the today's lunch menu for the selected restaurant.  
![6_admin_view_menu_exists_not_published_dishes_exist](https://user-images.githubusercontent.com/60218699/111033961-8c273180-8424-11eb-8b79-668fbd35888d.png)

#### 7. The menu has been published  
After the menu published status has changed to 'true', the page no longer displays buttons for adding/updating/deleting dishes to the menu.  
Also the menu becomes available for viewing by users.  
![7_admin_view_menu_exists_published](https://user-images.githubusercontent.com/60218699/111033962-8e898b80-8424-11eb-902a-e97bfccbce3c.png)     
To change the menu admin presses "Change Menu" button.  
In this case frontend sends HTTP PUT request to update published status to 'false' for the today's lunch menu for the selected restaurant.  
After the menu published status has changed to 'false', the page starts displaying buttons for adding/updating/deleting dishes to the menu again.    
Also the menu becomes not available for viewing by users.  

#### 8. Admin creates/updates a restaurant  
When admin presses "Add Rest." or "Update Rest." buttons, a form for creating/updating a restaurant appears.  
After the admin creates or updates a restaurant he presses the "Save" button to save changes.  
In this case frontend sends HTTP POST or PUT request to create or update a restaurant.  
![8_admin_view_restaurant_create_form](https://user-images.githubusercontent.com/60218699/111033966-91847c00-8424-11eb-86a9-ca233979e834.png)

#### 9. Admin deletes a restaurant  
To delete a restaurant admin must press "Delete Rest." button.    
In this case frontend sends HTTP DELETE request to delete the selected restaurant.  
![9_admin_deletes_restaurant](https://user-images.githubusercontent.com/60218699/111033968-934e3f80-8424-11eb-9103-97e66cd27ca6.png)