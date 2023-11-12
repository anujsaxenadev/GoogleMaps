// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(constants.PluginIds.hilt_android) version Versions.hilt apply false
    id(constants.PluginIds.ksp) version Versions.ksp apply false
}