// D:\Lab09\app\build.gradle.kts

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // ELIMINADA: alias(libs.plugins.kotlin.compose)
}

android { // <-- INICIO DEL BLOQUE ANDROID
    namespace = "com.example.lab09"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lab09"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    composeOptions {
        // Corrección de la compatibilidad Kotlin/Compose Compiler (Versión 1.5.4 para Kotlin 1.9.20)
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

} // <-- FINAL DEL BLOQUE ANDROID

dependencies {
    // 1. ELIMINADA: implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    //    -> USANDO: implementation(libs.androidx.lifecycle.runtime.ktx)

    // 2. COROUTINES: Versión 1.7.3 es antigua. No la corregiremos aquí, pero es un punto débil.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // 3. RETROFIT y GSON (OK para mantener cableado si no está en libs.versions.toml)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // 4. COIL (OK, pero la versión 2.4.0 es antigua. Debería ser 2.6.0+)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // 5. NAVIGATION (OK, pero las versiones 2.7.x son antiguas. Mejor 2.7.4)
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.4")
    implementation("androidx.navigation:navigation-compose:2.7.4")

    // 6. ACTIVITY BASE (CRUCIAL PARA enableEdgeToEdge)
    //    Si `libs.androidx.activity.compose` no resuelve `enableEdgeToEdge`,
    //    añade la dependencia base explícitamente usando la versión 1.9.0 de tu TOML:
    implementation("androidx.activity:activity-ktx:1.9.0")

    // --- DEPENDENCIAS QUE USAN LIBS.VERSIONS.TOML ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx) // ÚNICA FUENTE DE VERDAD AHORA
    implementation(libs.androidx.activity.compose) // ÚNICA FUENTE DE VERDAD AHORA
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // --- TEST DEPENDENCIES ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}