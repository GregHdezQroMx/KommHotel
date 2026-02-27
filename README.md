# KommHotel - A Kotlin Multiplatform Hotel Booking App

KommHotel is a demonstration project built with Kotlin Multiplatform and Compose Multiplatform, showcasing a complete, end-to-end application that runs on Android, iOS, Desktop (JVM), and Web. It features a shared business logic layer, a shared UI, and a dedicated Ktor backend server.

## Features

- **User Authentication:** Secure user registration and login using JWT (JSON Web Tokens) and **BCrypt** password hashing (OWASP compliant).
- **Persistent Database:** Real-time data storage using **PostgreSQL** and Exposed ORM.
- **Session Management:** Persistent session management across all platforms (`DataStore` on Android, `NSUserDefaults` on iOS, `localStorage` on Web).
- **Room Listings:** Fetches and displays a list of available hotel rooms from the backend.
- **Authenticated Requests:** Fetches user-specific data (e.g., "My Bookings") by sending authenticated requests to a protected server endpoint.
- **Multi-platform UI:** A single, shared user interface built with Compose Multiplatform that works seamlessly across Android, iOS, Desktop, and Web.

---

## Tech Stack & Architecture

- **Kotlin Multiplatform:** For sharing code between platforms.
- **Compose Multiplatform:** For building a declarative, shared UI.
- **Ktor:** Used for both the backend server and the multiplatform HTTP client.
- **Exposed:** Kotlin SQL framework for PostgreSQL integration.
- **BCrypt:** Secure password hashing following OWASP recommendations.
- **Koin:** For Dependency Injection across all modules.
- **Kotlinx Serialization:** For JSON parsing.
- **Coroutines:** For asynchronous operations.
- **Clean Architecture Principles:** The project is structured into three main modules:
    - `:shared`: A KMP module containing the core business logic, data layer (repositories, data sources), and presentation logic (ViewModels).
    - `:server`: A Ktor-based backend server with PostgreSQL persistence.
    - `:composeApp`: The main application module containing the shared UI.

---

## Database Setup (PostgreSQL)

To run the project with persistence, you must have PostgreSQL installed and configured on your development machine.

### 1. Installation (macOS via Homebrew)

```bash
brew install postgresql
brew services start postgresql
```

### 2. Database Creation

Access the PostgreSQL console:

```bash
psql postgres
```

Inside the prompt (`postgres=#`), execute:

```sql
CREATE DATABASE kommhotel;
\password <your_mac_username>
```
*Note: Set the password to `password` as per current server configuration.*

### 3. Verification

To check registered users and their hashed passwords:

```bash
psql -U <your_mac_username> kommhotel
```

Run the query:
```sql
SELECT id, first_name, last_name, email, password FROM users;
```

---

## Getting Started: How to Run

### 1. Run the Backend Server

1. Ensure PostgreSQL is running.
2. Update `DatabaseFactory.kt` with your local database username if necessary.
3. Open a terminal and run:

    ```bash
    ./gradlew :server:run
    ```

### 2. Run the Android App

1. Select the `composeApp` run configuration.
2. Click the "Run" button (▶️).

---
## Next Steps

1. **Multiplatform Database:** Implement SQLDelight in the `:shared` module for offline caching on mobile and web.
2. **Payment Integration:** Mock or integrate a payment gateway for the booking flow.
3. **Enhance UI/UX:** Add animations and a polished design system.
