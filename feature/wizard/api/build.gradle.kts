plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.motsi.core.wizard.api"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(libs.kotlinx.serialization.json)
}