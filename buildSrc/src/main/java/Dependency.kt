object Dependency {
    object GradlePlugins {
        const val ANDROID_APPLICATION = "com.android.application"
        const val ANDROID_LIBRARY = "com.android.library"
        const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    }

    object AndroidX {
        const val KOTLIN_CORE = "androidx.core:core-ktx:${Versions.Kotlin}"
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.Appcompat}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.Constraint}"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Versions.Material}"
    }

    object Test {
        const val JUNIT = "junit:junit:${Versions.Junit}"
    }

    object AndroidTest {
        const val TEST_JUNIT = "androidx.test.ext:junit:${Versions.TestJunit}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.Espresso}"
    }
}