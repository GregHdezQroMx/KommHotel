# KommHotel - A Kotlin Multiplatform Hotel Booking App

KommHotel is a demonstration project built with Kotlin Multiplatform and Compose Multiplatform, showcasing a complete, end-to-end application that runs on Android, iOS, Desktop (JVM), and Web. It features a shared business logic layer, a shared UI, and a dedicated Ktor backend server.

## Features

- **User Authentication:** Secure user registration and login using JWT (JSON Web Tokens).
- **Session Management:** Persistent session management across all platforms (`DataStore` on Android, `NSUserDefaults` on iOS, `localStorage` on Web).
- **Room Listings:** Fetches and displays a list of available hotel rooms from the backend.
- **Authenticated Requests:** Fetches user-specific data (e.g., "My Bookings") by sending authenticated requests to a protected server endpoint.
- **Multi-platform UI:** A single, shared user interface built with Compose Multiplatform that works seamlessly across Android, iOS, Desktop, and Web.

---

## Tech Stack & Architecture

- **Kotlin Multiplatform:** For sharing code between platforms.
- **Compose Multiplatform:** For building a declarative, shared UI.
- **Ktor:** Used for both the backend server and the multiplatform HTTP client.
- **Koin:** For Dependency Injection across all modules.
- **Kotlinx Serialization:** For JSON parsing.
- **Coroutines:** For asynchronous operations.
- **Clean Architecture Principles:** The project is structured into three main modules:
    - `:shared`: A KMP module containing the core business logic, data layer (repositories, data sources), and presentation logic (ViewModels).
    - `:server`: A Ktor-based backend server that provides the API endpoints for authentication and data.
    - `:composeApp`: The main application module containing the shared UI and platform-specific entry points.

---

## Getting Started: How to Run

Follow these steps to build and run the project on all supported platforms.

### Prerequisites

- **JDK 17 or higher.**
- **Android Studio** (latest stable version recommended).
- **Xcode** (for running the iOS app).

### 1. Run the Backend Server

The server must be running for any of the client apps to function.

1.  Open a terminal within Android Studio.
2.  Execute the following Gradle task:

    ```
    ./gradlew :server:run
    ```

3.  The server will start on `http://localhost:8080` (accessible as `0.0.0.0`). The client apps are already configured to connect to the correct IP address for each platform. Keep this terminal open.

### 2. Run the Android App

1.  Select the `composeApp` run configuration in Android Studio.
2.  Choose an Android emulator or a connected physical device.
3.  Click the "Run" button (▶️).

### 3. Run the iOS App

**Note:** Requires a macOS machine with Xcode installed.

1.  Open the iOS project in Xcode by opening the `.xcodeproj` file located in `composeApp/ios/`.
2.  Select a simulator (e.g., "iPhone 15 Pro") and a team for signing.
3.  Click the "Run" button (▶️) in Xcode.

### 4. Run the Desktop App

1.  Open a new terminal.
2.  Execute the following Gradle task:

    ```
    ./gradlew :composeApp:run
    ```

### 5. Run the Web App

1.  Open a new terminal.
2.  Execute the following Gradle task. The `--continuous` flag enables hot-reloading.

    ```
    ./gradlew :composeApp:jsBrowserDevelopmentRun --continuous
    ```

3.  Once the build is complete, the terminal will output a URL (usually `http://localhost:8088/`). Open this URL in your web browser.

---
## Screenshots

*To add the screenshots, create a `/screenshots` folder in the root of the project and place the image files inside.*

**Login Screen (iOS)**

<img src="./screenshots/Login%20iOs.png" width="300">

**Home Screen (Android)**

<img src="./screenshots/Room%20List%20Android.png" width="300">

**Room Detail (Web)**

<img src="./screenshots/Room%20details%20Web.png" width="500">

**My Bookings Screen (Web)**

<img src="./screenshots/My%20Bookings%20Web.png" width="500">

**Signup Screen (Desktop)**

<img src="./screenshots/Signup%20Desktop.png" width="500">

---

## Next Steps

This project serves as a strong foundation. Here are some potential improvements and next steps:

1.  **Production-like Network Testing:**
    - To enable testing from physical Android/iOS devices, deploy the server to a dedicated machine on the local network (e.g., a Linux server).
    - Build a distributable JAR for the server using `./gradlew :server:installDist`.
    - Run the server on the Linux machine and update the `getBaseUrl()` function in the client apps to point to the server's network IP address.

2.  **Refine Server-Side Logic:** 
    - The `GET /me/bookings` endpoint currently returns all bookings. Refactor it to filter bookings based on the `userId` or `email` from the JWT principal.
    - Similarly, update the `POST /bookings` endpoint to associate the new booking with the authenticated user.

3.  **Implement Logout Feature:**
    - Add a "Logout" button to the UI (e.g., in the `MyBookingsScreen`).
    - On click, call `sessionManager.clearToken()`.
    - Navigate the user back to the `LoginScreen`.

4.  **Database Integration:**
    - Replace the in-memory `userStorage` and `bookings` lists in the server with a persistent database solution like H2 (for development) or a more robust option like PostgreSQL.
    - Consider a multiplatform database for the client-side, like SQLDelight, for caching or offline capabilities.

5.  **Enhance UI/UX:**
    - Add animations and transitions between screens.
    - Improve error handling with more user-friendly dialogs or snackbars instead of just text.
    - Implement a more polished design system.

6.  **Add Testing:**
    - Write unit tests for ViewModels and Repositories in the `shared` module.
    - Add UI tests for key user flows.
