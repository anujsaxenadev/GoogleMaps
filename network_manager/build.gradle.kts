plugins {
    `android-library`
}

apply<plugin.LibraryGradlePlugin>()

android {
    namespace = "com.wordpress.anujsaxenadev.network_manager"
}

dependencies {
    // Network
    okhttp()

    // DI
    hilt()

    // Utils
    common()
}