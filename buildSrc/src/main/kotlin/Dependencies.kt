import extensions.annotationProcessor
import extensions.implementation
import extensions.ksp
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val coreKotlin = "androidx.core:core-ktx:${Versions.coreKotlin}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val activityKotlinExtension = "androidx.activity:activity-ktx:${Versions.activityKotlinExtension}"
    const val fragmentKotlinExtension = "androidx.fragment:fragment-ktx:${Versions.fragmentKotlinExtension}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hiltCompiler}"
    const val dataBinding = "androidx.databinding:databinding-compiler:${Versions.dataBinding}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomAnnotationProcessor = "androidx.room:room-compiler:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKotlinExtension = "androidx.room:room-ktx:${Versions.room}"
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineCore}"
}

fun DependencyHandler.room(){
    implementation(Dependencies.roomRuntime)
    annotationProcessor(Dependencies.roomAnnotationProcessor)
    ksp(Dependencies.roomCompiler)
    implementation(Dependencies.roomKotlinExtension)
}

fun DependencyHandler.hilt(){
    implementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)
}

fun DependencyHandler.okhttp(){
    implementation(Dependencies.okhttp)
}

fun DependencyHandler.coroutine(){
    implementation(Dependencies.coroutineCore)
}

fun DependencyHandler.kotlinViewExtensions(){
    implementation(Dependencies.activityKotlinExtension)
    implementation(Dependencies.fragmentKotlinExtension)
}

fun DependencyHandler.material(){
    implementation(Dependencies.material)
}

fun DependencyHandler.appCompat(){
    implementation(Dependencies.appCompat)
}

fun DependencyHandler.coreKotlin(){
    implementation(Dependencies.coreKotlin)
}

fun DependencyHandler.dataBinding(){
    implementation(Dependencies.dataBinding)
}