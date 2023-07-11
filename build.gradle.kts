 buildscript {
    dependencies {
        classpath(Dependency.Hilt.HILT_ANDROID_GRADLE)
        classpath(Dependency.GradlePlugins.GOOGLE_PLUGIN)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Dependency.GradlePlugins.ANDROID_APPLICATION) version Versions.Android apply false
    id(Dependency.GradlePlugins.ANDROID_LIBRARY) version Versions.Android apply false
    id(Dependency.GradlePlugins.KOTLIN_ANDROID) version Versions.AndroidKotlin apply false
}