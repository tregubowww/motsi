package com.example.motsi.conventions

import com.android.build.gradle.LibraryExtension
import com.example.motsi.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.plugins.ksp.get().pluginId)
            apply(plugin = libs.plugins.kotlin.compose.get().pluginId)
            apply(plugin = libs.plugins.kotlinx.serialization.get().pluginId)

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
                buildFeatures {
                    compose = true
                }
            }

            dependencies {
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:wrappers"))
                "implementation"(project(":core:navigation"))
                "implementation"(project(":core:common"))
                "implementation"(project(":core:network"))
                "implementation"(project(":core:di"))

                "implementation"(libs.androidx.compose.navigation)
                "implementation"(libs.kotlinx.serialization.json)
                "implementation"(libs.kotlinx.collections.immutable)
                "implementation"(libs.retrofit)
                "implementation"(libs.dagger)
                "ksp"(libs.dagger.compiler)

                "implementation"(libs.androidx.lifecycle.runtime.ktx)
                "implementation"(libs.androidx.activity.compose)
                "implementation"(platform(libs.androidx.compose.bom))
                "implementation"(libs.androidx.ui)
                "implementation"(libs.androidx.ui.graphics)
                "implementation"(libs.androidx.ui.tooling.preview)
                "implementation"(libs.androidx.material3)
                "testImplementation"(libs.junit)
            }
        }
    }
}
