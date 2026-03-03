import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    androidTarget { 
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    js(IR) {
        browser()
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.datetime)
            implementation(libs.koin.core)
            implementation(libs.compose.ui)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.components.resources)

            // Ktor Client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        // Nuevo source set compartido para la web (JS y Wasm)
        val webMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }

        jsMain.get().dependsOn(webMain)
        wasmJsMain.get().dependsOn(webMain)
        
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.koin.android)
            implementation(libs.okio)
            implementation(libs.ktor.client.android)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.androidx.datastore.preferences)
            implementation(libs.okio)
            implementation(libs.ktor.client.java)
        }
        
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.kommhotel.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false
        }
        debug {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

compose.resources.publicResClass = true
