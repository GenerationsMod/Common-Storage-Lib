//enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "common-storage-lib"

pluginManagement {
    repositories {
        maven(url = "https://maven.msrandom.net/repository/cloche")

        gradlePluginPortal()
    }
}

include("core", "data", "lookup", "resources")//, "test")