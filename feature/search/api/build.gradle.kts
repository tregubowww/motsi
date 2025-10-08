import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.motsi.feature.search.api"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(libs.kotlinx.serialization.json)
}