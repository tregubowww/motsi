package com.example.motsi.conventions

import com.android.build.gradle.LibraryExtension
import com.example.motsi.ext.configureKotlinAndroid
import com.example.motsi.ext.kotlinJvmCompilerOptions
import com.example.motsi.ext.libs
import com.example.motsi.ext.projectJavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget


class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.plugins.android.library.get().pluginId)
            apply(plugin = libs.plugins.kotlin.android.get().pluginId)
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }
            kotlinJvmCompilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
            }
        }
    }
}