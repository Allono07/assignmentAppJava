plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}


android {
    namespace = "com.devdroid.assignmentapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.devdroid.assignmentapp"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{

        viewBinding = true
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
}


dependencies {
    implementation("com.netcore.android:smartech-nudges:10.2.11")

//    // Remote SDK from GitHub
 implementation("com.github.Allono07:androidnetcoreloglibrary:v1.0.8")
    //implementation("com.netcore.android:smartech-nudges-compose:10.5.5")

    implementation ("androidx.work:work-runtime:2.7.0")
    implementation("com.netcore.android:smartech-sdk:3.7.6")
    implementation ("com.netcore.android:smartech-push:3.5.13")
    implementation("com.netcore.android:smartech-appinbox:3.5.4")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.webengage:android-sdk:4.+")
    implementation ("io.branch.sdk.android:library:5+")
    implementation ("com.google.gms:google-services:4.3.15")
    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation ("com.android.installreferrer:installreferrer:2.2")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("store.galaxy.samsung.installreferrer:samsung_galaxystore_install_referrer:4.0.0")

    // Required if your app is in the Google Play Store (tip: avoid using bundled play services libs):
    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation ("com.webengage:we-personalization:1.1.3")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("com.google.firebase:firebase-firestore:24.10.2")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation("androidx.core:core-ktx:1.17.0")
    implementation( "androidx.lifecycle:lifecycle-process:2.6.2")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    testImplementation("junit:junit:4.13.2")
//    implementation(project(":NetcoreSDKCapturer"))
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}