The schematic example of interaction between frontend and backend parts of application through REST API
=================================


#### 1. User visits the website  
When a user visits the website, the browser displays a page that contains a list of restaurants and today's lunch menu for the first restaurant on the list.    
To do this the frontend sends HTTP GET request to get a list of restaurants and HTTP GET request to get today's lunch menu for the first restaurant on the list.  
![0_user_view_app_open](https://user-images.githubusercontent.com/60218699/112733956-f1fed780-8f53-11eb-937d-0226ce4770b2.png)

#### 2. User selects a restaurant  
When a user selects a restaurant on the restaurants list, he will be presented today's lunch menu of that restaurant.  
To do this the frontend sends HTTP GET request to get today's lunch menu for the selected restaurant.  
If restaurant's today's lunch menu does not exist yet, a corresponding message will be displayed to the user.  
![1_user_view_menu_does_not_exist_or_not_published](https://user-images.githubusercontent.com/60218699/112733992-1c509500-8f54-11eb-9f59-34258e7b2ab1.png)
Otherwise, the user will be presented today's lunch menu of the selected restaurant.  
If restaurant's today's lunch menu presented, and the user is logged in, he can vote for restaurant by clicking the button "Vote!".  
In this case frontend sends HTTP POST request to register user vote for the selected restaurant.  
![2_user_view_menu_exists_published](https://user-images.githubusercontent.com/60218699/112734010-312d2880-8f54-11eb-8667-ce7ff9c823a7.png)

#### 3. Admin selects a restaurant  
When an admin selects a restaurant on the restaurants list, he will be presented today's lunch menu of that restaurant.  
To do this the frontend sends HTTP GET request to get today's lunch menu for the selected restaurant.  
If restaurant's today's lunch menu does not exist yet, a corresponding message will be displayed to the admin.  
Admin can create today's lunch menu for the selected restaurant by clicking the button "Add Menu".  
In this case frontend shows admin a frontend's form to create today's lunch menu for the selected restaurant.  
![3_admin_view_menu_does_not_exist](https://user-images.githubusercontent.com/60218699/112734019-3e4a1780-8f54-11eb-9940-4afbc71a2d71.png)

#### 4. Frontend's form to create a menu    
Admin sees frontend's form to create a menu (no dishes have been added to the menu at the moment).   
![4_admin_view_menu_exists_not_published_dishes_do_not_exist](https://user-images.githubusercontent.com/60218699/112734021-45712580-8f54-11eb-8f75-dcdb55f2fae6.png)

#### 5. Admin creates a dish for the menu on frontend   
When admin presses "Add Dish" or "Update" (on dish list) buttons, a frontend's form to create/update a dish appears.  
After the admin creates or updates a dish he presses the "Save" button to save changes on frontend.   
![5_admin_view_menu_exists_not_published_dish_create_form](https://user-images.githubusercontent.com/60218699/112734088-b6b0d880-8f54-11eb-9492-440331d90a33.png)

#### 6. Admin publishes the menu  
After adding all dishes to the today's lunch menu of the selected restaurant, admin presses the "Publish Menu" button to create/update the menu on backend.  
In this case frontend sends HTTP POST/PUT request to create/update the today's lunch menu with dishes for the selected restaurant.  
![6_admin_view_menu_exists_not_published_dishes_exist](https://user-images.githubusercontent.com/60218699/112734027-55890500-8f54-11eb-8e71-374e9c237ec8.png)

#### 7. The menu has been created on backend  
Menu has been created and returned to the frontend.  
To change the menu admin presses "Change Menu" button.  
In this case frontend shows admin a frontend's form to update menu.  
To delete the menu admin presses "Delete Menu" button.  
In this case frontend sends HTTP DELETE request to delete the menu.  
![7_admin_view_menu_exists_published](https://user-images.githubusercontent.com/60218699/112750096-2368b900-8fcf-11eb-8000-a8865bc82e12.png)
To change the menu admin presses "Change Menu" button.  
In this case frontend shows admin a frontend's form to update menu.  

#### 8. Admin creates/updates a restaurant  
When admin presses "Add Rest." or "Update Rest." buttons, a frontend's form to create/update a restaurant appears.  
After the admin creates or updates a restaurant he presses the "Save" button to save changes.  
In this case frontend sends HTTP POST or PUT request to create or update a restaurant.  
![8_admin_view_restaurant_create_form](https://user-images.githubusercontent.com/60218699/112734038-63d72100-8f54-11eb-874f-791c4131da95.png)

#### 9. Admin deletes a restaurant  
To delete a restaurant admin must press "Delete Rest." button.    
In this case frontend sends HTTP DELETE request to delete the selected restaurant.  
![9_admin_deletes_restaurant](https://user-images.githubusercontent.com/60218699/112734161-1e672380-8f55-11eb-89b8-6d5a0a2b067e.png)