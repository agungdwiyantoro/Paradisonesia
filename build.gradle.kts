buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.1")
        classpath("com.google.gms:google-services:4.3.13")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
        classpath("com.google.firebase:perf-plugin:1.4.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
//        jcenter()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}