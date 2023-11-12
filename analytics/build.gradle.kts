plugins {
    `android-library`
}

apply<plugin.LibraryGradlePlugin>()

android {
    namespace = "com.wordpress.anujsaxenadev.analytics"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // DI
    hilt()
}