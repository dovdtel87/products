PRESENT YOUR WORK

## Project specifications
- **Language**: Kotlin
- **IDE**: Android Studio
- **Design Pattern**: MVVM (Model - View - ViewModel)
- **DI**: Dagger Hilt
- **Concurrency**: Coroutines + StateFlow
- **UI**: Jetpack Compose
- **Database**: Room

## Use Cases
I have developed an Android app using Jetpack Compose to retrieve information about products from a remote API, persisting them locally to allow the app to continue working offline.
The user can interact with the list of products displayed, adding or removing them from the basket.
Each product can have or not have a discount, meeting specific criteria. As the discount information is not provided in the network response, and no other endpoint has been provided, a Fake discount repository has been created.
The app can handle two types of discounts:
- **FreeItem**: If you buy a certain number of products, you get a certain number of products for free.
- **PriceReduction**: If you buy at least a certain number of products, you get a discount on each product.

The discount repository is designed in a way that the products with discounts and the criteria to meet the discounts can be changed.
There could be multiple ways to handle this solution, so I decided to implement the following use cases for this project:
- If there is no connection (or other error) and no products are stored in the database, an error is displayed on the screen.
- If there is no connection (or other error) and products are stored in the database, then that list is presented.
- If there is a connection, the list is retrieved from the API.
- If taps in the + button that item is added to the basket and the total amount is updated.
- If taps in the - button that item is removed to the basket and the total amount is updated.
- If the product has a discount the inforamtion about the discount is displayed.
- If the product purchased meets the criteria to display the discount that info is shown to the user.
## Architecture
The project has been developed following the concerns of clean architecture with an MVVM architecture and a repository pattern implementation.
To communicate the view with the view model, "StateFlow" has been used. The state of the app is a `SealedClass` that can have three states: `Loading`, `Content`, `Error`.
- `Loading`: Shows a `CircularProgressIndicator`.
- `Error`: Shows an icon, a message, and a retry button.
- `Content`: Displays the list of products.

The project has two repositories, one for handling products and another one for handling discounts. The decision to use two separate repositories is because one is well-defined with its API, while the other one should be mocked to retrieve the discount data. The project implements the repository pattern.

Between the ViewModel and Repository, another "UseCase" layer has been created to handle the different use cases that the app can handle.

Three different types of models have been defined:
- `Dtos`: e.g., `ProductDto`, to handle data received from the API.
- Domain model: e.g., `Product`, to handle the domain data.
- UI model: e.g., `ProductUI`, to handle the visual representation of the data.

## Developer Good Practices
- Single Responsibility Principle: Each class and function have a single responsibility, making the code easier to understand, test, and maintain. For example:
  - The `ListProductsViewModel` is responsible for managing the app's state and interacting with the repository.
  - The `ProductsRepository` is responsible for fetching product data from the API and performing database operations.
- Separation of concerns: The code is organized into separate layers, each in their own package, to ensure clear separation of concerns and maintainability.
- KISS Principle: The code in the app follows the KISS principle by prioritizing simplicity and readability. Complex logic is avoided, and code is kept concise and easy to understand.
- DRY (Don't Repeat Yourself) Principle: The DRY principle is applied throughout the codebase by promoting code reusability and avoiding duplication. For example, the UI components of Loading and Error can be reusable.
- Other things taken into account:
  - Dependency Injection: Dagger Hilt is used for dependency injection, making it easier to manage and scale the project.
  - Asynchronous Programming: Coroutines are used for handling asynchronous operations, such as fetching data from the API and performing database operations.
  - Error Handling: Errors during API calls are handled by displaying an error message in the UI if there are no available products in the database; otherwise, a cached list of products is shown.

## Tests
Unit tests have been provided for all the layers.
Also, a simple UI test to ensure the list is displayed on the screen has been provided.

## Scalability
- The current implementation allows for easy expansion of the API, repository, and view model to support other product-related functionalities.
- The discount repository is implemented in a way that its data can be moved to an API and consumed from there.
- To create a more complex app, navigation should be implemented, although for this project, the skeleton of navigation has been provided.


