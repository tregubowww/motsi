plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.motsi.feature.messages.api"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}