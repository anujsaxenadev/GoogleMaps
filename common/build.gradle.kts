plugins {
    `java-library`
    id(constants.PluginIds.kotlin_jvm)
}

java {
    sourceCompatibility = Versions.javaVersion
    targetCompatibility = Versions.javaVersion
}

dependencies{
    // Async
    coroutine()
}