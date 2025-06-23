# Social Media API - Spring Boot

This is a Spring Boot application that provides a RESTful API for a basic social media platform. It includes functionalities for managing posts, comments, likes, users, and administrative tasks, with robust JWT-based authentication and authorization.

## Features

*   **User Management:** User registration, login, and role-based access control.
*   **Post Management:** Create, view, update, and delete posts.
*   **Comment Management:** Add, view, update, and delete comments on posts.
*   **Like Management:** Like and unlike posts.
*   **Role-Based Authorization:** Differentiates between `USER` and `ADMIN` roles.
*   **JWT Authentication:** Secure API access using JSON Web Tokens.
*   **Password Hashing:** Secure storage of user passwords using BCrypt.
*   **Enhanced Post Deletion Security:** Ensures only post owners or administrators can delete posts.

## Technologies Used

*   **Spring Boot:** Framework for building the application.
*   **Spring Security:** For authentication and authorization.
*   **Spring Data JPA:** For database interaction and ORM.
*   **MySQL:** Relational database to store application data.
*   **Lombok:** To reduce boilerplate code (getters, setters, constructors).
*   **JJWT:** Java library for JSON Web Tokens.
*   **Maven:** Build automation tool.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before you begin, ensure you have the following installed:

*   **Java Development Kit (JDK) 17 or higher**
*   **Maven 3.6.x or higher**
*   **MySQL Server**

### Cloning the Repository

```bash
git clone <repository-url> # Replace with your actual repository URL
cd spring-boot-app
```

### Database Setup

1.  **Create a MySQL database:**

    ```sql
    CREATE DATABASE social_app_db;
    ```

2.  **Update `application.properties`:**
    Open `src/main/resources/application.properties` and configure your MySQL database connection details. The default values are:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/social_app_db?useSSL=false&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=password
    ```
    Adjust `username` and `password` if your MySQL setup is different.

    The `spring.jpa.hibernate.ddl-auto=update` property will automatically create/update the necessary tables (`users`, `roles`, `posts`, `comments`, `likes`, `user_roles`) when the application starts.

### Building and Running the Application

Navigate to the root of the `spring-boot-app` directory in your terminal and run the following Maven command to build the project:

```bash
mvn clean install -DskipTests
```

After a successful build, you can run the application using:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080` by default.

## API Endpoints

All API endpoints are prefixed with `/api`.

### Authentication

*   **Register a new user:**
    *   `POST /api/auth/register`
    *   **Body:** `{"username": "testuser", "password": "password123", "email": "test@example.com"}`
    *   **Response:** `"User registered successfully!"`

*   **Login user and get JWT:**
    *   `POST /api/auth/login`
    *   **Body:** `{"username": "testuser", "password": "password123"}`
    *   **Response:** `"<YOUR_JWT_TOKEN>"`

### Posts

*   **Create a new post:**
    *   `POST /api/posts`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`, `Content-Type: application/json`
    *   **Body:** `{"title": "My New Post", "content": "This is the content of my post."}`
    *   **Authorization:** `ROLE_USER`, `ROLE_ADMIN`

*   **Get all posts:**
    *   `GET /api/posts`
    *   **Response:** List of `PostDTO` objects.

*   **Get post by ID:**
    *   `GET /api/posts/{id}`
    *   **Response:** `PostDTO` object.

*   **Update a post:**
    *   `PUT /api/posts/{id}`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`, `Content-Type: application/json`
    *   **Body:** `{"title": "Updated Title", "content": "Updated content."}`
    *   **Authorization:** `ROLE_USER` (owner only), `ROLE_ADMIN`

*   **Delete a post:**
    *   `DELETE /api/posts/{id}`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`
    *   **Authorization:** `ROLE_USER` (owner only), `ROLE_ADMIN`

### Comments

*   **Add a comment to a post:**
    *   `POST /api/posts/{postId}/comments`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`, `Content-Type: application/json`
    *   **Body:** `{"content": "This is a comment."}`
    *   **Authorization:** `ROLE_USER`, `ROLE_ADMIN`

*   **Get all comments for a post:**
    *   `GET /api/posts/{postId}/comments`
    *   **Response:** List of `CommentDTO` objects.

*   **Update a comment:**
    *   `PUT /api/posts/{postId}/comments/{commentId}`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`, `Content-Type: application/json`
    *   **Body:** `{"content": "Updated comment content."}`
    *   **Authorization:** `ROLE_USER` (owner only), `ROLE_ADMIN`

*   **Delete a comment:**
    *   `DELETE /api/posts/{postId}/comments/{commentId}`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`
    *   **Authorization:** `ROLE_USER` (owner only), `ROLE_ADMIN`

### Likes

*   **Toggle a like on a post (like/unlike):**
    *   `POST /api/posts/{postId}/likes`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`
    *   **Authorization:** `ROLE_USER`, `ROLE_ADMIN`
    *   **Response:** `"Post liked successfully!"` or `"Like removed successfully!"`

*   **Check if user liked a post:**
    *   `GET /api/posts/{postId}/likes/status`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`
    *   **Authorization:** `ROLE_USER`, `ROLE_ADMIN`
    *   **Response:** `true` or `false`

### Admin Endpoints

*   **Get all users:**
    *   `GET /api/admin/users`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`
    *   **Authorization:** `ROLE_ADMIN`

*   **Delete a user:**
    *   `DELETE /api/admin/users/{id}`
    *   **Headers:** `Authorization: Bearer <YOUR_JWT_TOKEN>`
    *   **Authorization:** `ROLE_ADMIN`

## Security Features Implemented

*   **JWT-based Authentication:** All protected endpoints require a valid JSON Web Token for access.
*   **Role-Based Authorization:** Access to certain endpoints (e.g., admin functionalities, post/comment/like creation/modification) is restricted based on user roles (`ROLE_USER`, `ROLE_ADMIN`).
*   **BCrypt Password Encoding:** User passwords are securely stored in the database using BCrypt hashing.
*   **Custom `JwtAuthenticationFilter`:** Intercepts requests to validate JWTs and set up Spring Security context.
*   **`JwtAuthenticationEntryPoint`:** Handles unauthorized access attempts by returning a `401 Unauthorized` response.
*   **Ownership-Based Authorization for Content:**
    *   `PostService`: `updatePost` and `deletePost` methods ensure that only the creator of a post (or an `ADMIN`) can modify or delete it.
    *   `CommentService`: `updateComment` and `deleteComment` methods ensure that only the creator of a comment (or an `ADMIN`) can modify or delete it.
*   **Eager Fetching for Authorization Checks:** The `User` associated with a `Post` is eagerly fetched to ensure accurate ownership checks during authorization.

## Future Enhancements

*   Implement refresh tokens for JWTs.
*   Add more robust input validation using Spring Boot Validation annotations.
*   Implement global exception handling for more user-friendly error messages.
*   Add pagination and sorting to API endpoints.
*   Implement rate limiting for API calls.
*   Configure CORS policies.
*   Integrate with a logging framework for better monitoring.
*   Add unit and integration tests.



