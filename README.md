# Board Games Forum (Spring Boot)

A simple web forum for board game enthusiasts, built with Java and Spring Boot. 
The application allows users to register, log in, create topics, post replies,
and search both topics and posts. All data is stored in memory (no database required).

## Features

- User registration and login (with password hashing)
- Three user roles: ADMIN, MODERATOR, USER
- Create new topics and reply to existing ones
- Full-text search in both topic titles and post contents
- Forum rules and FAQ sections (sample content)
- All data stored in memory (no database setup needed)
- Simple, responsive UI with custom CSS

## Technologies Used

- Java 24
- Spring Boot 3.5.3
- Maven
- Lombok
- Thymeleaf (server-side rendering)
- CSS (custom styles)

## Project Structure

- `src/main/java/com/asia/forum/boardgames/`
  - `App.java` – Main application entry point
  - `configuration/` – Spring configuration (AppConfiguration)
  - `controllers/` – Web controllers (Authentication, Main, Content)
  - `dao/` – In-memory repositories for users, topics, and posts (no database)
  - `model/` – Data models: User, Topic, Post
  - `session/` – (Session management)
- `src/main/resources/`
  - `static/` – CSS styles and images (logo, banner)
  - `templates/` – Thymeleaf HTML templates (main, topic, login, register, FAQ, etc.)

## How It Works

- **Authentication:**
  - Users register and log in with a username, email, and password (hashed for security).
  - Three roles exist: ADMIN, MODERATOR, USER. Roles are assigned in code (no admin panel).
- **Forum:**
  - Logged users can create new topics and reply to existing ones.
  - All topics and posts are stored in memory (Java collections).
  - FAQ is available from the navigation bar.
- **Search:**
  - The search bar allows searching for keywords in both topic titles and post contents.
  - Results are shown on the main page, listing all matching topics.
- **No avatars, email verification, or password reset.**
- **No moderation panel or admin-only features.**

## Example Login Credentials

You can use these accounts to log in and test different roles:

| Login             | Password   | Role      |
|-------------------|------------|-----------|
| admin             | admin      | ADMIN     |
| asia123           | asia123    | USER      |
| PierwszyModerator | moderator  | MODERATOR |
| janusz            | janusz123  | USER      |

## Running the Application

1. Make sure you have Java 24 and Maven installed.
2. Clone the repository or download the source code.
3. In the project root, run:

   ```bash
   mvn spring-boot:run
   ```

4. Open your browser and go to [http://localhost:8080/main](http://localhost:8080/main)

## Plans to Extend the Application
(Will be implemented in future versions, during the Java Junior Bootcamp)

- **Add a Database:**
  - Connect to a database
- **Add Pagination:**
  - Implement pagination for topics and posts to handle large data sets.
- **Add Moderation/Admin Panel:**
  - Create views and endpoints for moderators/admins to manage users, topics, and posts.
- **Add Registration Validation:**
  - Add validation for user registration (e.g., unique usernames, email format).
- **Refactor to suit MVC Pattern:**
  - Refactor the code to better adhere to the MVC pattern, separating concerns more clearly.
- **Add Tests:**
  - Write unit and integration tests using JUnit and Spring Boot Test.

## License

This project is for educational purposes.

