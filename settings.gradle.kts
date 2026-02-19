pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io") // <-- JitPack repository
    }
}

rootProject.name = "KommHotel"

include(":composeApp")
include(":server")
include(":shared")
include(":iosApp") // <-- Register the iosApp module
