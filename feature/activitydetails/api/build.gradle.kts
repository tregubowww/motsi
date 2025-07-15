import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.motsi.feature.activitydetails.api"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(project(":core:navigation"))
    testImplementation(libs.junit)
}