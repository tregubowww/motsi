import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.motsi.android.feature)
}

android {
    namespace = "com.example.motsi.feature.search.impl"
}

dependencies {
    api(project(":feature::search:api"))
    implementation(project(":feature:activitydetails:api"))

    implementation(libs.org.osmdroid)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.androidx.preference.ktx)
}
