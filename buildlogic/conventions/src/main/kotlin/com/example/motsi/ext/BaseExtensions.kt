package com.example.motsi.ext

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.versions.compileSdk.get().toInt()
        defaultConfig {
            this as ApplicationDefaultConfig

            targetSdk = libs.versions.targetSdk.get().toInt()
            compileSdk = libs.versions.compileSdk.get().toInt()
            minSdk = libs.versions.minSdk.get().toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            versionCode = libs.versions.appVersionCode.get().toInt()
            versionName = libs.versions.appVersionName.get()
        }

        compileOptions {
            sourceCompatibility = projectJavaVersion
            targetCompatibility = projectJavaVersion
        }
    }
}


fun Project.kotlinJvmCompilerOptions(block: KotlinJvmCompilerOptions.() -> Unit) {
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions(block)
    }
}

val Project.libs: org.gradle.accessors.dm.LibrariesForLibs
    get() =
        (this as ExtensionAware).extensions.getByName("libs") as org.gradle.accessors.dm.LibrariesForLibs

val Project.projectJavaVersion: JavaVersion
    get() = JavaVersion.toVersion(libs.versions.java.get())