plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.neds.raceapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.neds.raceapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.neds.raceapp.MyTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
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

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    implementation("androidx.compose.material3:material3:1.1.2") // Latest stable Material 3

    // Koin for Dependency Injection
    implementation("io.insert-koin:koin-android:3.4.0")
    implementation("io.insert-koin:koin-androidx-compose:3.4.0")

    // Retrofit & Moshi for Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // OkHttp and Logging Interceptor for API logs
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Gson for JSON Parsing
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.compose.ui:ui-test-junit4-android:1.7.7")

    // Unit Testing
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("io.mockk:mockk:1.12.0")
    testImplementation ("org.mockito:mockito-inline:2.13.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation ("app.cash.turbine:turbine:1.0.0")
    debugImplementation ("androidx.test:core:1.3.0")

    // UI Testing
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.3")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.3")
    testImplementation("org.robolectric:robolectric:4.10")

    // Espresso (Android UI Testing)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    // Compose UI Test
    androidTestImplementation ("androidx.compose.ui:ui-test-manifest:1.5.3")
}