# KommHotel - A Kotlin Multiplatform Hotel Booking App

KommHotel is a demonstration project built with Kotlin Multiplatform and Compose Multiplatform, showcasing a complete, end-to-end application that runs on Android, iOS, Desktop (JVM), and Web. It features a shared business logic layer, a shared UI, and a dedicated Ktor backend server.

## Features

- **User Authentication:** Secure user registration and login using JWT (JSON Web Tokens) and **BCrypt** password hashing (OWASP compliant).
- **Persistent Database:** Real-time data storage using **PostgreSQL** and Exposed ORM.
- **Back-Office Dashboard (JS/Wasm):** A dedicated administrative interface for hotel managers, supporting both high-performance **Wasm** and high-compatibility **JavaScript**.
- **Session Management:** Persistent session management across all platforms (`DataStore` on Android, `NSUserDefaults` on iOS, `localStorage` on Web).
- **Multi-platform UI:** A single, shared user interface built with Compose Multiplatform that works seamlessly across all targets.

---

## Tech Stack & Architecture

- **Kotlin Multiplatform:** Sharing business logic and data layers across platforms.
- **Compose Multiplatform:** Shared UI for Mobile, Desktop, and Web.
- **Kotlin/Wasm & JS:** Dual-target web implementation for the Back-Office dashboard using a shared `webMain` source set.
- **Ktor:** Used for both the backend server and the multiplatform HTTP client.
- **Exposed:** Kotlin SQL framework for PostgreSQL integration.
- **BCrypt:** Secure password hashing following OWASP recommendations.
- **Koin:** Dependency Injection across all modules.

---

## Database Setup (PostgreSQL)

To run the project with persistence, you must have PostgreSQL installed and configured.

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
Inside the prompt, execute:
```sql
CREATE DATABASE kommhotel;
\password <your_username>
```

---

## Getting Started: How to Run

### 1. Run the Backend Server

```bash
./gradlew :server:run
```

### 2. Run the Back-Office (Web Targets)

You can choose between the Wasm or JS implementation for the manager dashboard:

**For Wasm (Recommended for performance):**
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

**For JavaScript (Maximum compatibility):**
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
```

### 3. Run the Android App

1. Select the `composeApp` run configuration in Android Studio.
2. Click the "Run" button (▶️).

---

## Administrative Features (Back-Office)

The Web targets have been transformed into a management tool including:
- **Occupancy Metrics:** Real-time tracking of hotel room availability.
- **Guest Experience Management:** Monitoring and resolving guest feedback and complaints.
- **Inventory Control:** Direct management of room rates and availability.
- **Remote Branding:** Centralized configuration of logos and themes for all client apps.

---
## Next Steps

1. **Remote Configuration:** Fully integrate the branding service to allow real-time theme updates from the dashboard.
2. **Booking Analytics:** Implement visual reports and charts for monthly revenue and booking trends.
3. **Guest Communication:** Add a notification system to respond directly to guest feedback from the back-office.
