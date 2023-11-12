plugins {
    `android-library`
}

apply<plugin.LibraryGradlePlugin>()

android {
    namespace = "com.wordpress.anujsaxenadev.database_manager"
}

dependencies {
    // Storage
    room()

    // DI
    hilt()

    // Utils
    common()
}