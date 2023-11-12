package plugin

import com.android.build.gradle.LibraryExtension
import constants.PluginIds
import constants.ProjectConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        applyPlugins(target)
        target.android().apply{
            updateJavaVersion()
            updateBuildTypeConfigurations()
            updateDefaultConfig()
        }
    }

    private fun applyPlugins(project: Project){
        project.apply{
            plugin(PluginIds.ksp)
            plugin(PluginIds.kotlin_android)
        }
    }

    private fun LibraryExtension.updateJavaVersion(){
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    private fun LibraryExtension.updateBuildTypeConfigurations(){
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }

    private fun LibraryExtension.updateDefaultConfig(){
        defaultConfig {
            minSdk = ProjectConfig.minSdk
            compileSdk = ProjectConfig.compileSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    fun Project.android(): LibraryExtension =
        extensions.getByType(LibraryExtension::class.java)
}