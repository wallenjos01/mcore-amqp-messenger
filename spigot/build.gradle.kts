import build.plugin.Common
import build.plugin.MultiShadow

plugins {
    id("mod-build")
    id("mod-shadow")
    id("mod-multi-version")
    id("mod-publish")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://libraries.minecraft.net/")
}

Common.setupResources(project, rootProject, "plugin.yml")
MultiShadow.setupShadow(project, multiVersion, 8, "1.8-1.16")
MultiShadow.setupShadow(project, multiVersion, 17, "1.7-1.20.4")

tasks.shadowJar {
    archiveClassifier.set("1.20.5-1.20.6")
}

dependencies {

    implementation(project(":common"))
    shadow(project(":common")) { isTransitive = false }
    shadow(libs.rabbitmq.client) { isTransitive = false }

    compileOnly(libs.midnight.core)

    java8CompileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    java17CompileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    java21CompileOnly("org.spigotmc:spigot-api:1.20.6-R0.1-SNAPSHOT")
}