import build.plugin.Common

plugins {
    id("mod-build")
    id("mod-publish")
    id("mod-shadow")
    id("mod-fabric")
}

Common.setupResources(project, rootProject, "fabric.mod.json")

dependencies {

    implementation(project(":common"))
    shadow(project(":common")) { isTransitive = false }
    shadow(libs.rabbitmq.client) { isTransitive = false }

    compileOnly(libs.midnight.core)

    minecraft("com.mojang:minecraft:1.20.6")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.15.10")
}