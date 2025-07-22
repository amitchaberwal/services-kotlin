plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

class HelloWorldPlugin: Plugin<Project>{
    override fun apply(target: Project) {
        print("Hello World")
    }
}

apply<HelloWorldPlugin>()

android {
    namespace = "com.amit.basic"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.amit.basic"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("staging"){
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
//    flavorDimensions += "default"
    flavorDimensions += listOf("default","style")
    productFlavors {
        create("uat"){
            dimension = "default"
            applicationIdSuffix = ".uat"
            buildConfigField("String","BASE_URL", "https://uedutect.com/")
        }
        create("prod"){
            dimension = "default"
            buildConfigField("String","BASE_URL", "https://edutect.com/")
        }

        create("red"){
            dimension = "style"
            buildConfigField("String","Style", "Red")
        }
        create("green"){
            dimension = "style"
            buildConfigField("String","Style", "Green")
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
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Work Manager
    implementation("androidx.work:work-runtime:2.10.2")
    //For image widget
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2 ")

    // Coroutine in scope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.1")

    //Viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2")

//    // for our library
//    implementation(project(":amit_library"))
}