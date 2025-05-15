import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.kotlinx.serialization)
//    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.motsi.feature.search.api"

//    //убрать в плагин фичи когда появится
//    buildFeatures {
//        compose = true
//    }
}

dependencies {
//    implementation(project(":core"))
    implementation(project(":core:navigation"))

    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}