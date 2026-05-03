import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.motsi.android.feature)
}
android {
    namespace = "com.example.motsi.core.wizard.impl"
}

dependencies {
    api(project(":feature::wizard:api"))

    implementation(libs.maps.mobile)
    implementation(libs.coil)
    implementation(libs.coil.compose)
}