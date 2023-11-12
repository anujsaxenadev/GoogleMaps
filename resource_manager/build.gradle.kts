plugins {
    `android-library`
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
}

apply<plugin.LibraryGradlePlugin>()

android {
    namespace = "com.wordpress.anujsaxenadev.resource_manager"
}

dependencies {
    hilt()
    common()
}