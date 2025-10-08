
plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.motsi.feature.addsportactivity.api"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(libs.kotlinx.serialization.json)
}