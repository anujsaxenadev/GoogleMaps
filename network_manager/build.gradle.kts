plugins {
    `android-library`
}

apply<plugin.LibraryGradlePlugin>()

android {
    namespace = "com.wordpress.anujsaxenadev.network_manager"

    dataBinding{
        enable  = true
    }
}

dependencies {
    okhttp()
    hilt()
    common()
}