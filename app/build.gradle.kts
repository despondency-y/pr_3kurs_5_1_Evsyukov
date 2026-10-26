plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.pr_3kurs_5_1_evsyukov"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.pr_3kurs_5_1_evsyukov"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation ("com.google.code.gson:gson:2.8.5")
    implementation("androidx.room:room-runtime:2.5.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation("androidx.room:room-ktx:2.5.0")

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}