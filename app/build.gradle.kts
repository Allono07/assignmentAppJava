plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.devdroid.assignmentapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.devdroid.assignmentapp"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {
    implementation ("io.branch.sdk.android:library:5+'")// Check for latest version before hard-coding

    implementation ("store.galaxy.samsung.installreferrer:samsung_galaxystore_install_referrer:4.0.0")

    // Required if your app is in the Google Play Store (tip: avoid using bundled play services libs):
    implementation ("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}