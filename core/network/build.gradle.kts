import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.motsi.core.network"

    buildFeatures {
        buildConfig = true
        compose = true

    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://dev.api.example.com/\"")
        }
        getByName("release") {
            buildConfigField("String", "BASE_URL", "\"https://api.example.com/\"")
        }
    }


}

dependencies {


    implementation (libs.dagger)
    ksp (libs.dagger.compiler)


    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(project(":core:common"))
    implementation(project(":core:wrappers"))
    implementation(project(":core:di"))
}