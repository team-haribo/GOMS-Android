import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.presentation"
    compileSdk = 33

    defaultConfig {
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLIENT_ID", getKey("CLIENT_ID"))
        buildConfigField("String", "REDIRECT_URL", getKey("REDIRECT_URL"))
        buildConfigField("String", "BASE_URL", getKey("BASE_URL"))
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

fun getKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    // gauth
    implementation(Dependency.Libraries.GAUTH)
    // code scanner
    implementation(Dependency.Libraries.CODE_SCANNER)
    // permission
    implementation(Dependency.Libraries.TEDPERMMISION)
    // circle image
    implementation(Dependency.Libraries.CIRCLE_IMAGEVIEW)
    // zxing
    implementation(Dependency.Libraries.ZXING)

    // lifecycle
    implementation(Dependency.LifeCycle.LIFECYCLE)
    implementation(Dependency.LifeCycle.LIFECYCLE_LIVEDATA)
    implementation(Dependency.LifeCycle.LIFECYCLE_RUNTIME)
    implementation(Dependency.LifeCycle.LIFECYCLE_VIEWMODEL_KTX)

    // hilt
    implementation(Dependency.Hilt.HILT_ANDROID)
    kapt(Dependency.Hilt.HILT_ANDROID_COMPILER)

    // coil
    implementation(Dependency.Coil.COIL_COMPOSE)
    implementation(Dependency.Coil.COIL_VIEW)

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
    debugImplementation(Dependency.Compose.COMPOSE_TOOLING)
    debugImplementation(Dependency.Compose.COMPOSE_MANIFEST)
    testImplementation(Dependency.ComposeTest.COMPOSE_JUNIT)

    // navigation
    implementation(Dependency.AndroidX.NAVIGATION_FRAGMENT)
    implementation(Dependency.AndroidX.NAVIGATION_UI_KTX)

    implementation(Dependency.AndroidX.KOTLIN_CORE)
    implementation(Dependency.AndroidX.APPCOMPAT)
    implementation(Dependency.AndroidX.ACTIVITY_KTX)
    implementation(Dependency.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependency.Google.MATERIAL)
    testImplementation(Dependency.Test.JUNIT)
    androidTestImplementation(Dependency.AndroidTest.TEST_JUNIT)
    androidTestImplementation(Dependency.AndroidTest.ESPRESSO)

    implementation(Dependency.AndroidX.SWIPE_REFRESH_LAYOUT)
    implementation("com.google.accompanist:accompanist-swiperefresh:0.27.0")

    implementation(platform(Dependency.Google.FIREBASE_BOM))
    implementation(Dependency.Google.FIREBASE_ANALYTICS)
}