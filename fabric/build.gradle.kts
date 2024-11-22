import build.plugin.Common

plugins {
    id("mod-build")
    id("mod-publish")
    id("mod-shadow")
    id("mod-fabric")
}

Common.setupResources(project, rootProject, "fabric.mod.json")

dependencies {

    minecraft("com.mojang:minecraft:1.21.3")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.16.9")

    implementation(libs.smi.amqp)

    shadow(libs.smi.amqp) { isTransitive = false }
    shadow(libs.rabbitmq.client) { isTransitive = false }

    compileOnly(libs.midnight.core)
}