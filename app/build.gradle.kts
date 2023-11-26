plugins {
    id("com.android.application")
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
    workManager()

    implementation("com.airbnb.android:lottie:6.2.0")


}