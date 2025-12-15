pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://artifacts.netcore.co.in/artifactory/android") }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "assignmentApp"
include(":app")


//include(":NetcoreSDKCapturer")
