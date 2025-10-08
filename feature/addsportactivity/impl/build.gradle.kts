plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.motsi.android.feature)
}
android {
    namespace = "com.example.motsi.feature.addsportactivity.impl"
}

dependencies {
    api(project(":feature::addsportactivity:api"))

    implementation(libs.maps.mobile)
    implementation(libs.coil)
    implementation(libs.coil.compose)
}