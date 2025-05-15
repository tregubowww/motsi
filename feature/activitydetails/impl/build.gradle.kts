plugins {
    alias(libs.plugins.motsi.android.library)

    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.motsi.feature.activitydetails.impl"

    //убрать в плагин фичи когда появится
    buildFeatures {
        compose = true
    }
}

dependencies {
    api(project(":feature::activitydetails:api"))

    implementation(project(":core:ui"))
    implementation(project(":core:di"))
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))

    implementation(libs.androidx.compose.navigation)
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
}