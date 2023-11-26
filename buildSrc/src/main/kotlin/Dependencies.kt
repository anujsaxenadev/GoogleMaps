import extensions.addModuleImplementation
import extensions.annotationProcessor
import extensions.dependencyImplementation
import extensions.ksp
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    const val coreKotlin = "androidx.core:core-ktx:${Versions.coreKotlin}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val activityKotlinExtension = "androidx.activity:activity-ktx:${Versions.activityKotlinExtension}"
    const val fragmentKotlinExtension = "androidx.fragment:fragment-ktx:${Versions.fragmentKotlinExtension}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val dataBinding = "androidx.databinding:databinding-compiler:${Versions.dataBinding}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomAnnotationProcessor = "androidx.room:room-compiler:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKotlinExtension = "androidx.room:room-ktx:${Versions.room}"
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineCore}"
    const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
    const val hiltWorkManagerExtension = "androidx.hilt:hilt-work:${Versions.hiltWorkManagerExtension}"
}

fun DependencyHandler.room(){
    dependencyImplementation(Dependencies.roomRuntime)
    annotationProcessor(Dependencies.roomAnnotationProcessor)
    ksp(Dependencies.roomCompiler)
    dependencyImplementation(Dependencies.roomKotlinExtension)
}

fun DependencyHandler.hilt(){
    dependencyImplementation(Dependencies.hiltAndroid)
    ksp(Dependencies.hiltCompiler)
}

fun DependencyHandler.okhttp(){
    dependencyImplementation(Dependencies.okhttp)
}

fun DependencyHandler.coroutine(){
    dependencyImplementation(Dependencies.coroutineCore)
}

fun DependencyHandler.kotlinViewExtensions(){
    dependencyImplementation(Dependencies.activityKotlinExtension)
    dependencyImplementation(Dependencies.fragmentKotlinExtension)
}

fun DependencyHandler.material(){
    dependencyImplementation(Dependencies.material)
}

fun DependencyHandler.appCompat(){
    dependencyImplementation(Dependencies.appCompat)
}

fun DependencyHandler.coreKotlin(){
    dependencyImplementation(Dependencies.coreKotlin)
}

fun DependencyHandler.dataBinding(){
    dependencyImplementation(Dependencies.dataBinding)
}

fun DependencyHandler.resourceManager(){
    addModuleImplementation(":resource_manager")
}

fun DependencyHandler.networkManager(){
    addModuleImplementation(":network_manager")
}

fun DependencyHandler.databaseManager(){
    addModuleImplementation(":database_manager")
}

fun DependencyHandler.analytics(){
    addModuleImplementation(":analytics")
}

fun DependencyHandler.common(){
    addModuleImplementation(":common")
}

fun DependencyHandler.workManager(){
    dependencyImplementation(Dependencies.workManager)
    dependencyImplementation(Dependencies.hiltWorkManagerExtension)
}