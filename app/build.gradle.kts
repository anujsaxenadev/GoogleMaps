import extensions.addModuleImplementation

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
    packaging {
        resources.excludes.add("META-INF/*.md")
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
    coreKotlin()
    appCompat()
    material()

    kotlinViewExtensions()
    hilt()
    dataBinding()

    resourceManager()
    networkManager()
    databaseManager()
    analytics()
    common()
}