import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.motsi.android.feature)
}

android {
    namespace = "com.example.motsi.feature.activitydetails.impl"
}

dependencies {
    api(project(":feature::activitydetails:api"))

    implementation(libs.coil)
    implementation(libs.coil.compose)
}