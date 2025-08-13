plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.shahpourkhast.rohamfood"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.shahpourkhast.rohamfood"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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

    //Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    //-----------------------------------------------------------------
    //Navigation Components
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.navigation.ui)
    //-----------------------------------------------------------------
    //viewModel for Fragment
    implementation(libs.androidx.fragment.ktx)
    //-----------------------------------------------------------------
    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //-----------------------------------------------------------------
    //Glide
    implementation(libs.glide)
    implementation(libs.glide.transformations)
    //-----------------------------------------------------------------
    //Server
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    //-----------------------------------------------------------------
    //sdp & sspS
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    //-----------------------------------------------------------------
    //Bottom Navigation
    implementation(libs.library)
    //-----------------------------------------------------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}