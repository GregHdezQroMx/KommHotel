plugins {
    id("org.jetbrains.kotlin.jvm")
    id("io.ktor.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
    application // Add the application plugin to configure distributions
}

dependencies {
    implementation(project(":shared"))
    // Ktor Server Dependencies
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.auth) // <-- ADDED FOR JWT
    implementation(libs.ktor.server.auth.jwt) // <-- ADDED FOR JWT
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.logback)

    // Exposed (for database access)
    implementation(libs.jetbrains.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    // PostgreSQL Driver
    implementation(libs.postgresql)

    // BCrypt for password hashing
    implementation(libs.bcrypt)

    // Test Dependencies
    testImplementation(libs.ktor.server.testHost)
    testImplementation(libs.kotlin.testJunit)
}

application {
    mainClass.set("com.kommhotel.server.ApplicationKt")
}

// Configure the distribution to exclude duplicate files
distributions {
    main {
        contents {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}
