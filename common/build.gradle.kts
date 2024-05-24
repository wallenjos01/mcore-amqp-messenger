plugins {
    id("mod-build")
    id("mod-publish")
    id("mod-multi-version")
}

dependencies {

    compileOnlyApi(libs.rabbitmq.client)

    compileOnlyApi(libs.midnight.cfg)
    compileOnlyApi(libs.midnight.lib)
    compileOnlyApi(libs.midnight.core)

    compileOnlyApi(libs.slf4j.api)
    compileOnlyApi(libs.jetbrains.annotations)
}