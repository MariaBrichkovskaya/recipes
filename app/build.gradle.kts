plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.receipts"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.receipts"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.fragment:fragment-testing:1.8.5")
    implementation("androidx.test.ext:junit-ktx:1.2.1")
    testImplementation("com.google.dagger:hilt-android-testing:2.44")
    kapt("androidx.room:room-compiler:2.6.1")
    kapt("com.android.databinding:compiler:3.1.4")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    //LifeCycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-common:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.squareup.retrofit2:converter-moshi:2.6.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //для загрузки картинок из апи
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.44")

    // Для Mockito и JUnit
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("org.mockito:mockito-android:3.12.4")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.powermock:powermock-module-junit4:2.0.9")
    testImplementation ("org.powermock:powermock-api-mockito2:2.0.9")
}