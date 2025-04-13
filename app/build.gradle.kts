import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.application)
    alias(libs.plugins.ksp)
}


android {
    namespace = "com.example.motsi.app"

    buildFeatures {
        compose = true
    }
    defaultConfig {
        applicationId = libs.versions.applicationId.get()
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
    }
}


dependencies {
    implementation(libs.androidx.compose.navigation)

    implementation (libs.dagger)
    ksp (libs.dagger.compiler)


    implementation(project(":core:ui"))
    implementation(project(":core:common"))
    implementation(project(":feature:search:impl"))
    implementation(project(":feature:messages:impl"))
    implementation(project(":feature:activitydetails:impl"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
}