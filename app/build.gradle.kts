import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.application)
    alias(libs.plugins.ksp)
}


android {
    namespace = "com.example.motsi.app"

    buildFeatures {
        compose = true
        buildConfig = true
    }
    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        val mapkitApiKey: String = project.findProperty("MAPKIT_API_KEY") as String
        buildConfigField("String", "MAPKIT_API_KEY", "\"$mapkitApiKey\"")
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}


dependencies {
    implementation(libs.androidx.compose.navigation)

    implementation (libs.dagger)
    ksp (libs.dagger.compiler)

    implementation(libs.androidx.core.splashscreen)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)

    //Maps mobile for integration
    implementation(libs.maps.mobile)

    // регион core
    implementation(project(":core:wrappers"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:di"))

    // регион feature в алфавитном порядке
    implementation(project(":feature:activitydetails:impl"))
    implementation(project(":feature:messages:impl"))
    implementation(project(":feature:search:impl"))


}