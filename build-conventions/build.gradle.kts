repositories {
    gradlePluginPortal()
}

plugins {
    `kotlin-dsl`
    alias(libs.plugins.vanniktechPublish) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.jooqCodegen) apply false
}

dependencies {
    // This is required for these plugins to be on the convention plugins ' classpath.
    implementation(libs.plugin.vanniktechPublish)
    implementation(libs.plugin.spring.boot)
    implementation(libs.plugin.jooqCodegen)
    // See https://github.com/grade/grodle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}