import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)

    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.parcelize)
}

android {
    namespace = "com.example.motsi.core.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:wrappers"))

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.org.osmdroid)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.activity.compose)
}