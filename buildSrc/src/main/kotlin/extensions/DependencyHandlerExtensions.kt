package extensions

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.dependencyImplementation(dependency: String){
    add("implementation", dependency)
}


fun DependencyHandler.annotationProcessor(dependency: String){
    add("annotationProcessor", dependency)
}

fun DependencyHandler.ksp(dependency: String){
    add("ksp", dependency)
}


fun DependencyHandler.addModuleImplementation(moduleName: String) {
    add("implementation", project(moduleName))
}