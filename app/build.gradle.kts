plugins {
    kotlin("kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.vcco.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vcco.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
        buildConfigField("String", "API_KEY", "\"5662967fcb674a3d31dbb0f174e451ef\"")
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //Android Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //UI
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //viewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Retrofit
    implementation(libs.retrofit)

    //Gson
    implementation(libs.gson)
    implementation(libs.gson.converter)

    //OkHTTP
    implementation(libs.okHttp)
    implementation(libs.okHttp.logging.interceptor)

    //RXJava
    implementation(libs.rxjava)
    implementation(libs.rxjava.adapter)

    //RxAndroid
    implementation(libs.rxandroid)

    //Streams
    implementation(libs.androidx.lifecycle.reactive.streams)

    //Multidex
    implementation(libs.androidx.multidex)

    //Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compilation)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.support.fragments)

    //Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
}

kapt {
    correctErrorTypes = true
}