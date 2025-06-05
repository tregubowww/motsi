import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)

    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.motsi.feature.search.impl"

    //убрать в плагин фичи когда появится
    buildFeatures {
        compose = true
    }
}

dependencies {
    api(project(":feature::search:api"))

    implementation(project(":core:ui"))
    implementation(project(":core:wrappers"))
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":feature:activitydetails:api"))

    implementation(libs.androidx.compose.navigation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.collections.immutable)

//ksp
    implementation(project(":core:di"))
//    ksp(project(":core:di"))

    implementation(libs.retrofit)
//    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
//    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


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
