plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.conversationalai"

    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.conversationalai"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        // Required for Java 8+ APIs
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // -----------------------------
    // AndroidX
    // -----------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)

    // -----------------------------
    // Material Design
    // -----------------------------
    implementation(libs.material)

    // -----------------------------
    // Retrofit
    // -----------------------------
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // -----------------------------
    // OkHttp Logging
    // -----------------------------
    implementation(libs.okhttp.logging)

    // -----------------------------
    // Kotlin Coroutines
    // -----------------------------
    implementation(libs.coroutines.android)

    // -----------------------------
    // Unit Testing
    // -----------------------------
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // -----------------------------
    // Java Desugaring
    // -----------------------------
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
}