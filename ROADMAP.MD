# KommHotel - Project Roadmap

## 🎯 Objetivo
KommHotel is a demonstration project built with Kotlin Multiplatform and Compose Multiplatform, showcasing a complete, end-to-end application that runs on Android, iOS, Desktop (JVM), and Web. It features a shared business logic layer, a shared UI, and a dedicated Ktor backend server.

---

## 🚀 Fase 1: Infraestructura y Persistencia ✅
**Estado:** Completado.
- [x] **Base de Datos:** Migración oficial a **PostgreSQL** mediante Exposed ORM.
- [x] **Seguridad:** Implementación de hashing de contraseñas con **BCrypt** bajo recomendaciones OWASP.
- [x] **Configuración:** Usuario administrador `ghetz` y base de datos `kommhotel` configurados.

---

## 🖥️ Fase 2: Ecosistema Web (Back-Office) ✅
**Estado:** Completado.
- [x] **Dual Target:** Soporte para **Kotlin/Wasm** y **JavaScript**.
- [x] **Arquitectura:** Centralización en `webMain` para evitar duplicidad de código.
- [x] **UI:** Dashboard administrativo profesional con métricas de ocupación y feedback.

---

## 🌟 Fase 3: Gestión de la Experiencia del Huésped 🛠️
**Objetivo:** Implementar el flujo de feedback y mejora continua siguiendo Clean Architecture.

- [ ] **Domain Layer:** Crear `shared/domain/src/commonMain/kotlin/features/feedback/model/Feedback.kt`.
- [ ] **Network Layer:** Crear DTOs en `shared/network-models/src/commonMain/kotlin/dto/`.
- [ ] **Data Layer (Server):** Implementar `FeedbackTable` y Repositorios en `server/src/main/kotlin/com/kommhotel/features/feedback/data/`.
- [ ] **UI (Mobile):** Formulario de quejas y sugerencias en `composeApp/commonMain/features/feedback/ui/`.
- [ ] **UI (Web Dashboard):** Gestión de estados de feedback (Pendiente, En Revisión, Resuelto).

---

## 🎨 Fase 4: Personalización y Branding Remoto (SDUI) 🎨
**Objetivo:** Configuración dinámica de UI para todos los clientes desde el Back-Office.

- [ ] **Branding Service:** Implementar lógica en el servidor para servir colores, logos y temas.
- [ ] **Admin UI:** Panel en el Dashboard para previsualizar y aplicar cambios de marca.
- [ ] **Integration:** Los clientes (Android/iOS) consumirán el `AppConfig` remoto para aplicar `MaterialTheme`.

---

## 📈 Fase 5: Operaciones e Inteligencia 📊
**Objetivo:** Herramientas de analítica y gestión de inventario.

- [ ] **Inventory CRUD:** Gestión de habitaciones y precios dinámicos.
- [ ] **Business Intelligence:** Reportes de ingresos y proyecciones de ocupación.

---

## 📝 Instrucciones de Mantenimiento
- **syncfail**: Error de sincronización, revisar logs y corregir.
- **buildfail**: Errores de compilación, recompilar y limpiar.
- **ruido**: Eliminar contextos residuales y re-enfocar.
- **logcat**: Inspección de logs en dispositivo.
- **update_road**: Sincronizar este archivo con el progreso actual.
