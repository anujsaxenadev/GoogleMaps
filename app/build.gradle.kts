plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.wordpress.anujsaxenadev.googlemaps"
    compileSdk = 34
    buildFeatures {
        dataBinding = true
    }
    defaultConfig {
        applicationId = "com.wordpress.anujsaxenadev.googlemaps"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(Dependencies.coreKotlin)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)

    implementation(Dependencies.activityKotlinExtension)
    implementation(Dependencies.fragmentKotlinExtension)


    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)


    ksp(Dependencies.dataBinding)



    implementation(project(":resource_manager"))
    implementation(project(":network_manager"))
    implementation(project(":database_manager"))
    implementation(project(":analytics"))
    implementation(project(":common"))
}