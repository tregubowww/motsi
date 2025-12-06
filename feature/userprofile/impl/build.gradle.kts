import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.motsi.android.feature)
}

android {
    namespace = "com.example.motsi.feature.userprofile.impl"
}

dependencies {
    api(project(":feature::userprofile:api"))

    implementation(libs.coil)
    implementation(libs.coil.compose)
}