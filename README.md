# Petstore REST API Automated Tests

This project contains automated tests for the [Swagger Petstore REST API](https://petstore.swagger.io/) using Java, JUnit 5, and Rest-assured.

## Project Overview

The tests are designed to validate the main functionalities of the Petstore API, focusing on user management and store order operations from a petshop. The project uses a data-driven approach with CSV files for easy maintenance and scalability.

## Test Coverage

### User API Tests (`TestUser.java`)

- **Create User (testCreateUserDDT):**  
  Parameterized test that creates users using data from `userMassa.csv`. Validates that each user is created successfully and the response contains the correct user ID.

- **Login User (testLoginUser):**  
  Logs in with a specific username and password. Checks if the login is successful by validating the response message and extracts the session token for further use.

- **Update User (testUpdateUser):**  
  Updates user information using data from `updateUser3.json`. Validates that the update operation is successful.

- **Delete User (testDeleteUser):**  
  Deletes a user and checks if the response confirms the deletion.

### Store API Tests (`TestStore.java`)

- **Create Order (testCreateOrderDDT):**  
  Parameterized test that creates store orders using data from `storeMassa.csv`. Validates all fields in the response to ensure the order was created correctly.

- **Find Order (testFindOrderDDT):**  
  Retrieves orders by ID using data from `storeMassa.csv` and checks if the returned data matches the expected values.

- **Delete Order (testDeleteOrder):**  
  Deletes a specific order by ID and validates the deletion response.

## Data-Driven Testing

- **CSV files** (`userMassa.csv`, `storeMassa.csv`) are used for parameterized tests, making it easy to add or modify test cases without changing the code.
- **JSON file** (`updateUser3.json`) is used for updating user data.


## Dependencies

- JUnit 5
- Rest Assured
- Hamcrest
- Gson

## Author

Ana Carollyne Guimarães de Souza

## License

This project is for educational