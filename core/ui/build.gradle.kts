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

    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen)
}