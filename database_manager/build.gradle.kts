import plugin.LibraryGradlePlugin

plugins {
    `android-library`
    id("dagger.hilt.android.plugin")
}

apply<LibraryGradlePlugin>()

android {
    namespace = "com.wordpress.anujsaxenadev.database_manager"
}

dependencies {
    room()
    hilt()
    common()
}