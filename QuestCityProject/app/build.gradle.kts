plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    kotlin("kapt") version "2.5.0"
}

android {
    namespace = "com.example.questcityproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.questcityproject"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        kapt {
//            arguments {arg("room.schemaLocation", "$projectDir/schemas")}
//        }
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
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
//    implementation(com.google.android.gms:play-services-maps:18.1.0)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation("org.osmdroid:osmdroid-android:6.1.16") // Основная библиотека OSMDroid
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.room:room-runtime:2.5.0") // Библиотека "Room"
//    kapt("androidx.room:room-compiler:2.5.0") // Кодогенератор
    implementation("androidx.room:room-ktx:2.5.0") // Дополнительно для Kotlin Coroutines, Kotlin Flows
}