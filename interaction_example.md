The schematic example of interaction between frontend and backend parts of application through REST API
=================================


#### 1. User visits the website  
When a user visits the website, the browser displays a page that contains a list of restaurants and today's lunch menu for the first restaurant on the list.    
To do this the frontend sends HTTP GET request to get a list of restaurants and HTTP GET request to get today's lunch menu for the first restaurant on the list.  
Show Picture 0

#### 2. User selects a restaurant  
When a user selects a restaurant on the restaurants list, he will be presented today's lunch menu of that restaurant.  
To do this the frontend sends HTTP GET request to get today's lunch menu for the selected restaurant.  
If restaurant's today's lunch menu does not exist yet or its published status is false, a corresponding message will be displayed to the user.  
Show Picture 1  
Otherwise, the user will be presented today's lunch menu of the selected restaurant.  
If restaurant's today's lunch menu presented, and the user is logged in, he can vote for restaurant by clicking the button "Vote!".
In this case frontend sends HTTP POST request to register user vote for the selected restaurant.  
Show Picture 2

#### 3. Admin selects a restaurant  
When an admin selects a restaurant on the restaurants list, he will be presented today's lunch menu of that restaurant.  
To do this the frontend sends HTTP GET request to get today's lunch menu for the selected restaurant.  
If restaurant's today's lunch menu does not exist yet, a corresponding message will be displayed to the admin.  
Admin can create today's lunch menu for the selected restaurant by clicking the button "Add Menu".  
In this case frontend sends HTTP POST request to create today's lunch menu for the selected restaurant.  
Show Picture 3

#### 4. The menu has been created    
Once created, the menu is displayed on the page (no dishes have been added to the menu at the moment).  
If menu's published status is 'false', the page displays buttons for adding/updating/deleting dishes to the menu.  
Show Picture 4  

#### 5. Admin creates a dish for the menu  
When admin presses "Add Dish" or "Update" (on dish list) buttons, a form for creating/updating a dish appears.  
After the admin creates or updates a dish he presses the "Save" button to save changes.  
In this case frontend sends HTTP POST or PUT request to create or update a dish for the today's lunch menu for the selected restaurant.  
Show Picture 5 

#### 6. Admin publishes the menu  
After adding all dishes to the today's lunch menu of the selected restaurant, admin presses the "Publish Menu" button to publish the menu.  
In this case frontend sends HTTP PUT request to update published status to 'true' for the today's lunch menu for the selected restaurant.  
Show Picture 6

#### 7. The menu has been published  
After the menu published status has changed to 'true', the page no longer displays buttons for adding/updating/deleting dishes to the menu.  
Also the menu becomes available for viewing by users.  
Show Picture 7  
To change the menu admin presses "Change Menu" button.  
In this case frontend sends HTTP PUT request to update published status to 'false' for the today's lunch menu for the selected restaurant.  
After the menu published status has changed to 'false', the page starts displaying buttons for adding/updating/deleting dishes to the menu again.    
Also the menu becomes not available for viewing by users.  

#### 8. Admin creates/updates a restaurant  
When admin presses "Add Rest." or "Update Rest." buttons, a form for creating/updating a restaurant appears.  
After the admin creates or updates a restaurant he presses the "Save" button to save changes.  
In this case frontend sends HTTP POST or PUT request to create or update a restaurant.  
Show Picture 8

#### 9. Admin deletes a restaurant  
To delete a restaurant admin must press "Delete Rest." button.    
In this case frontend sends HTTP DELETE request to delete the selected restaurant.  
Show Picture 9