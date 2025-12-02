plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.motsi.android.feature)
}
android {
    namespace = "com.example.motsi.feature.mysportactivities.impl"
}

dependencies {
    api(project(":feature::mysportactivities:api"))
    implementation(project(":feature:wizard:api"))

    implementation(libs.maps.mobile)
    implementation(libs.coil)
    implementation(libs.coil.compose)
}