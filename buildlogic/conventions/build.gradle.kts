import org.gradle.api.JavaVersion


plugins {
    `kotlin-dsl`
}
group = "com.example.motsi.buildlogic"

dependencies {
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationConventionPlugin") {
            id = libs.plugins.motsi.android.application.get().pluginId
            implementationClass = "com.example.motsi.conventions.AndroidApplicationConventionPlugin"
        }
        register("androidLibraryConventionPlugin") {
            id = libs.plugins.motsi.android.library.get().pluginId
            implementationClass = "com.example.motsi.conventions.AndroidLibraryConventionPlugin"
        }
        register("androidFeatureConventionPlugin") {
            id = libs.plugins.motsi.android.feature.get().pluginId
            implementationClass = "com.example.motsi.conventions.AndroidFeatureConventionPlugin"
        }
    }
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())
java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
}
