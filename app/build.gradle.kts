plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.about.libraries)
}

android {
    namespace = "com.bhuvaneshw.pdfviewer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bhuvaneshw.pdfviewer"
        minSdk = 26
        targetSdk = 35
        versionCode = 114
        versionName = "1.1.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Android UI
    implementation(libs.rangeseekbar)
    implementation(libs.colorpicker)
    implementation(libs.colorpicker.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // AboutLibraries
    implementation(libs.about.libraries.core)
    implementation(libs.about.libraries.compose.core)
    implementation(libs.about.libraries.compose.m3)

//    // Apache POI + DOCX to HTML (XDocReport)
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")


    // Alte module interne (dacă există)
    implementation(project(":core"))
    implementation(project(":ui"))
    implementation(project(":compose"))
    implementation(project(":compose-ui"))

    // Teste
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
