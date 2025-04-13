package com.example.motsi.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.example.motsi.ext.configureKotlinAndroid
import com.example.motsi.ext.kotlinJvmCompilerOptions
import com.example.motsi.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.example.motsi.ext.projectJavaVersion
import org.gradle.kotlin.dsl.configure


class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
                apply(plugin = libs.plugins.android.application.get().pluginId)
                apply(plugin = libs.plugins.kotlin.android.get().pluginId)
                apply(plugin = libs.plugins.kotlin.compose.get().pluginId)
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
            }
            kotlinJvmCompilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
            }
        }
    }
}