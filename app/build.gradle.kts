plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
}

apply<plugin.ApplicationGradlePlugin>()

android {
    namespace = "com.wordpress.anujsaxenadev.googlemaps"
}

dependencies {
    // Kotlin
    coreKotlin()
    kotlinViewExtensions()

    // UI
    appCompat()
    material()
    dataBinding()

    // DI
    hilt()

    // Storage
    resourceManager()
    databaseManager()

    // Network
    networkManager()

    // Logging
    analytics()

    // Utils
    common()
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    implementation("androidx.hilt:hilt-common:1.1.0-alpha01")
    implementation("androidx.hilt:hilt-work:1.1.0-alpha01")
    ksp("androidx.hilt:hilt-compiler:1.1.0-alpha01")

}