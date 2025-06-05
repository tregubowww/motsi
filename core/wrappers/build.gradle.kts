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


    implementation(project(":core:di"))
//    ksp(project(":core:di"))

//    implementation(project(":core:common"))
}