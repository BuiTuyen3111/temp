import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}

val keyPropertiesFile = rootProject.file("key.properties")
val keyProperties = Properties().apply {
    if (keyPropertiesFile.exists()) {
        load(keyPropertiesFile.inputStream())
    }
}

android {
    namespace = "com.zip.lock.screen.wallpapers"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zip.lock.screen.wallpapers"
        minSdk = 28
        targetSdk = 35
        versionCode = 22
        versionName = "1.2.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val dateFormat = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
        setProperty(
            "archivesBaseName",
            "Zipper_Lock$versionName($versionCode)_$dateFormat"
        )
    }
    signingConfigs {
        create("release") {
            storeFile = keyProperties["storeFile"]?.let { file(it as String) }
            storePassword = keyProperties["storePassword"] as String?
            keyAlias = keyProperties["keyAlias"] as String?
            keyPassword = keyProperties["keyPassword"] as String?
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        dataBinding = true
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.12.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // Okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Lottie
    implementation("com.airbnb.android:lottie:6.6.3")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    //Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Work Hilt
    implementation("androidx.work:work-runtime-ktx:2.10.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    implementation("me.relex:circleindicator:2.1.6")

    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-config-ktx")

    api("com.google.android.gms:play-services-ads:24.3.0")
    implementation("com.appsflyer:af-android-sdk:6.17.3")
    implementation("com.google.android.ump:user-messaging-platform:3.1.0")
    implementation("com.google.ads.mediation:applovin:13.3.1.1")
    implementation("com.google.ads.mediation:vungle:7.5.1.0")
    implementation("com.google.ads.mediation:facebook:6.20.0.0")
    implementation("com.google.ads.mediation:mintegral:16.9.91.1")
    implementation("com.google.ads.mediation:pangle:7.5.0.3.0")

    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))
    implementation("com.google.firebase:firebase-analytics")

    implementation("androidx.browser:browser:1.7.0")
    implementation("com.google.android.play:review:2.0.1")

    val room_version = "2.7.1"

    implementation("androidx.room:room-runtime:$room_version")

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:$room_version")

    // If this project only uses Java source, use the Java annotationProcessor
    // No additional plugins are necessary
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    implementation("androidx.lifecycle:lifecycle-process:2.8.3")
    implementation("com.google.android.play:app-update-ktx:2.1.0")
    implementation("androidx.paging:paging-runtime:3.2.1")

    //revenue cat
    implementation("com.revenuecat.purchases:purchases:8.19.2")
    implementation("com.revenuecat.purchases:purchases-ui:8.19.2")

    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-datasource:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")

    implementation("com.vanniktech:android-image-cropper:4.6.0")

    implementation("com.facebook.android:facebook-android-sdk:latest.release")

    val billing_version = "8.0.0"
    implementation("com.android.billingclient:billing-ktx:$billing_version")
}