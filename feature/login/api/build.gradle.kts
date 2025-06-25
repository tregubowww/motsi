import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.motsi.feature.login.api"
}

dependencies {
//    implementation(project(":core"))
    implementation(project(":core:navigation"))
}
dependencies {
    implementation(libs.kotlinx.serialization.json)
}