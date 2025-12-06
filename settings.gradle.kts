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

include(":feature:activitydetails:api")
include(":feature:activitydetails:impl")
include(":feature:userprofile:api")
include(":feature:userprofile:impl")
include(":feature:search:api")
include(":feature:search:impl")
include(":feature:messages:api")
include(":feature:messages:impl")
include(":feature:mysportactivities:api")
include(":feature:mysportactivities:impl")
include(":feature:wizard:api")
include(":feature:wizard:impl")

include(":core:ui")
include(":core:common")
include(":core:network")
include(":core:navigation")
include(":core:di")
include(":core:wrappers")
