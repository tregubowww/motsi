import com.example.motsi.ext.libs

plugins {
    alias(libs.plugins.motsi.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.motsi.core.wrappers"
}

dependencies {
    implementation (libs.dagger)
    ksp (libs.dagger.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.org.osmdroid)
    implementation(libs.services.location)
    implementation(libs.play.services.tasks)


    implementation(project(":core:di"))
    implementation(project(":core:common"))
}