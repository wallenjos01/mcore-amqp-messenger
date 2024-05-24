pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.wallentines.org/plugins")
        mavenLocal()
    }

    includeBuild("gradle/plugins")
}

rootProject.name = "amqp-messenger"

include("common")
include("fabric")
include("spigot")
include("velocity")