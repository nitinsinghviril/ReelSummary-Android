plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    // ... existing config ...
    
    defaultConfig {
        // ... existing config ...
        
        // Read API key from local.properties
        val localProperties = File(rootProject.rootDir, "local.properties")
        if (localProperties.exists()) {
            val properties = java.util.Properties()
            properties.load(localProperties.inputStream())
            buildConfigField("String", "GEMINI_API_KEY", "\"${properties.getProperty("GEMINI_API_KEY", "")}\"")
        } else {
            buildConfigField("String", "GEMINI_API_KEY", "\"\"")
        }
    }
    
    buildFeatures {
        buildConfig = true
    }
}

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.google.code.gson:gson:2.10.1")
}}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    
    // Networking (OkHttp only, no Retrofit)
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    
    // JSON
    implementation("com.google.code.gson:gson:2.10.1")
}
