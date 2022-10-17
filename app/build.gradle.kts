import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
}

android {
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.devfutech.paradisonesia"
        minSdk = 21
        targetSdk = 33
        versionCode = 2
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        viewBinding = true
    }



    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "11"
        }
    }

    externalNativeBuild {
        ndkBuild {
            path = file("src/main/jni/Android.mk")
        }
    }
    ndkVersion = "25.0.8775105"

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.5.2")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.2")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    implementation("com.google.firebase:firebase-auth:21.0.8")
    implementation("androidx.datastore:datastore-core:1.0.0-rc01")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    //Livedata
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

    // Room
    implementation("androidx.room:room-runtime:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

    // Paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")

    // Retrofit + GSON
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //HTTP Logging Interceptor
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    //Firebase
    implementation("com.google.firebase:firebase-analytics-ktx:21.1.1")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.2.13")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.8")
    implementation("com.google.firebase:firebase-perf-ktx:20.1.1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.0.8")
    implementation("com.google.firebase:firebase-bom:30.5.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.3.1")

    //Google Auth
    implementation("com.google.android.gms:play-services-auth:20.3.0")

    //Datetime
    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")

    //Scalable
    implementation("com.intuit.ssp:ssp-android:1.0.6")
    implementation("com.intuit.sdp:sdp-android:1.0.6")

    //Chuck
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    //Timber Log
    implementation("com.jakewharton.timber:timber:5.0.1")

    //Lottie Animation
    implementation("com.airbnb.android:lottie:4.1.0")

    //Facebook
    implementation("com.facebook.android:facebook-android-sdk:5.15.3")

    //Coil Kotlin Image Loader
    implementation("io.coil-kt:coil:1.3.2")

    //Shimmer Facebook
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    //Midtrans
    implementation("com.midtrans:uikit:1.29.3-SANDBOX")

    //Preference Androidx
    implementation("androidx.preference:preference:1.2.0")

    //Yahoo Range Seekbar
    implementation("com.yahoo.mobile.client.android.util.rangeseekbar:rangeseekbar-library:0.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

kapt {
    correctErrorTypes = true
}
