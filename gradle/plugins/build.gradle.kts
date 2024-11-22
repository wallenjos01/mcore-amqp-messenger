plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    implementation("org.wallentines:gradle-multi-version:0.3.0")
    implementation("org.wallentines:gradle-patch:0.2.0")
    implementation("com.gradleup.shadow:shadow-gradle-plugin:8.3.5")
    implementation("net.fabricmc:fabric-loom:1.8-SNAPSHOT")
}