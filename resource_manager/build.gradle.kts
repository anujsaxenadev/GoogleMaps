plugins {
    `android-library`
}

apply<plugin.LibraryGradlePlugin>()

android {
    namespace = "com.wordpress.anujsaxenadev.resource_manager"
}

dependencies {
    // DI
    hilt()

    // Utils
    common()
}