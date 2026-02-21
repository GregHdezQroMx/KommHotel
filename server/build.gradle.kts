plugins {
    id("org.jetbrains.kotlin.jvm")
    id("io.ktor.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":shared"))
    // Ktor Server Dependencies
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.contentNegotiation) // <-- CORRECTED
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.logback)

    // Test Dependencies
    testImplementation(libs.ktor.server.testHost) // <-- CORRECTED
    testImplementation(libs.kotlin.testJunit)
}

application {
    mainClass.set("com.kommhotel.server.ApplicationKt")
}
