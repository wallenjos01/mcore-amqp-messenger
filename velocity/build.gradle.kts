import build.plugin.Common

plugins {
    id("mod-build")
    id("mod-publish")
    id("mod-shadow")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

Common.setupResources(project, rootProject, "velocity-plugin.json")

dependencies {

    implementation(project(":common"))
    shadow(project(":common")) { isTransitive = false }
    shadow(libs.rabbitmq.client) { isTransitive = false }

    compileOnly(libs.midnight.core)

    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
}