pluginManagement {
    includeBuild("build-conventions")

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "spring-workspace"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include("udemy-spring-webflux")
