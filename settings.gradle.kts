pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://plugins.gradle.org/m2/") }
        mavenCentral()
    }
}

rootProject.name = "SlideTest"
include(":app")
include(":core:common")
include(":core:network")
include(":core:ui")
include(":core:data")
include(":core:domain")
include(":features:users")
