import com.example.motsi.ext.libs

plugins {
//    kotlin("jvm")
//
//    alias(libs.plugins.motsi.android.library)
//    kotlin("jvm")

    alias(libs.plugins.motsi.android.library)
//    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)


//
//    kotlin("jvm")
//    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
}

android {
    namespace = "com.example.motsi.core.di"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.android)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

}