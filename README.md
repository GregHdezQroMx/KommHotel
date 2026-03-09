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

## Roadmap & Next Steps

### Fase 1: Infraestructura y Persistencia ✅
- [x] Migración de almacenamiento en memoria a **PostgreSQL**.
- [x] Implementación de seguridad con **BCrypt** (Hashing de contraseñas).
- [x] Habilitación de targets **WasmJs** y **Js** para el entorno web.
- [x] Arquitectura de código compartido web mediante **`webMain`**.

### Fase 2: Dashboard de Gestión (En curso) 🛠️
- [x] Diseño de la interfaz principal del Administrador (Dashboard).
- [ ] **Gestión de Experiencia del Huésped:** Implementación de flujos para quejas, sugerencias y áreas de mejora.
- [ ] **Métricas en Tiempo Real:** Visualización de ocupación e ingresos desde la base de datos.

### Fase 3: Branding Remoto (Server-Driven UI) 🎨
- [ ] **Configurador de UI:** Cambiar logo, colores primarios y nombre del hotel desde el Dashboard.
- [ ] **Sincronización Global:** Aplicar los cambios de marca instantáneamente en las Apps de Android e iOS sin reinstalar.

### Fase 4: Inventario y Analítica 📈
- [ ] **Gestión de Habitaciones:** CRUD completo de inventario y precios dinámicos.
- [ ] **Reportes Avanzados:** Generación de PDFs y estadísticas de rendimiento mensual.
