plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.gsm_goms"
    compileSdk = Versions.CompleSdk

    defaultConfig {
        applicationId = "com.example.gsm_goms"
        minSdk = Versions.MinSdk
        targetSdk = Versions.TargetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    // gauth
    implementation(Dependency.Libraries.GAUTH)

    // lifecycle
    implementation(Dependency.LifeCycle.LIFECYCLE)
    implementation(Dependency.LifeCycle.LIFECYCLE_RUNTIME)
    implementation(Dependency.LifeCycle.LIFECYCLE_VIEWMODEL_KTX)

    // hilt
    implementation(Dependency.Hilt.HILT_ANDROID)
    implementation("com.android.support:support-annotations:28.0.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    kapt(Dependency.Hilt.HILT_ANDROID_COMPILER)

    // retrofit
    implementation(Dependency.Retrofit.RETROFIT_KT)
    implementation(Dependency.Retrofit.RETROFIT_GSON_CONVERTER)
    implementation(Dependency.Retrofit.OKHTTP)
    implementation(Dependency.Retrofit.OKHTTP_LOGGING_INTERCEPTOR)

    // compose
    implementation(Dependency.Compose.ACTIVITY_COMPOSE)
    implementation(Dependency.Compose.COMPOSE)
    implementation(Dependency.Compose.COMPOSE_TOOLING_PREVIEW)
    implementation(Dependency.Compose.COMPOSE_MATERIAL)
    implementation(Dependency.Compose.COMPOSE_LIVEDATA)
    implementation(Dependency.Compose.COMPOSE_NAV)
    implementation(Dependency.Compose.COMPOSE_ICON)
    implementation(Dependency.Compose.COMPOSE_PAGER)
    implementation(Dependency.Compose.PAGER_INDICATORS)
    testImplementation(Dependency.ComposeTest.COMPOSE_JUNIT)
    debugImplementation(Dependency.Compose.COMPOSE_TOOLING)
    debugImplementation(Dependency.Compose.COMPOSE_MANIFEST)

    implementation(Dependency.AndroidX.KOTLIN_CORE)
    implementation(Dependency.AndroidX.APPCOMPAT)
    implementation(Dependency.Google.MATERIAL)
    implementation(Dependency.AndroidX.CONSTRAINT_LAYOUT)
    testImplementation(Dependency.Test.JUNIT)
    androidTestImplementation(Dependency.AndroidTest.TEST_JUNIT)
    androidTestImplementation(Dependency.AndroidTest.ESPRESSO)

    implementation(Dependency.AndroidX.ACTIVITY_KTX)
}