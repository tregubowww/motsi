pluginManagement {
    includeBuild("buildlogic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "motsi"
include(":app")
include(":feature:search:api")
include(":feature:search:impl")
include(":feature:activitydetails:api")
include(":feature:activitydetails:impl")
include(":feature:messages:api")
include(":feature:messages:impl")
include(":core:ui")
include(":core:common")
