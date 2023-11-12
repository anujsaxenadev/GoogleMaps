package plugin

import Versions
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import constants.PluginIds
import constants.ProjectConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        applyPlugins(target)
        target.android().apply{
            enableBuildFeatures()
            updatePackagingOptions()
            updateJavaVersion()
            updateBuildTypeConfigurations()
            updateDefaultConfig()
        }
    }

    private fun applyPlugins(project: Project){
        project.apply{
            plugin(PluginIds.hilt)
            plugin(PluginIds.ksp)
            plugin(PluginIds.kotlin_android)
        }
    }

    private fun BaseAppModuleExtension.enableBuildFeatures(){
        buildFeatures {
            dataBinding = true
        }
    }

    private fun BaseAppModuleExtension.updatePackagingOptions(){
        packaging {
            resources.excludes.add("META-INF/*.md")
        }
    }

    private fun BaseAppModuleExtension.updateJavaVersion(){
        compileOptions {
            sourceCompatibility = Versions.javaVersion
            targetCompatibility = Versions.javaVersion
        }
    }

    private fun BaseAppModuleExtension.updateBuildTypeConfigurations(){
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

    private fun BaseAppModuleExtension.updateDefaultConfig(){
        defaultConfig {
            applicationId = ProjectConfig.appId
            targetSdk = ProjectConfig.targetSdk
            versionCode = ProjectConfig.versionCode
            versionName = ProjectConfig.versionName
            minSdk = ProjectConfig.minSdk
            compileSdk = ProjectConfig.compileSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    fun Project.android(): BaseAppModuleExtension =
        extensions.getByType(BaseAppModuleExtension::class.java)
}
