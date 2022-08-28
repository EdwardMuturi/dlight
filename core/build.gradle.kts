plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id(BuildPlugins.apollo).version(Versions.apolloVersion)
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    val GITHUB_TOKEN: String? =
        com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
            .getProperty("GITHUB_TOKEN")
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "GITHUB_TOKEN", GITHUB_TOKEN.toString())
        }

        debug{
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "GITHUB_TOKEN", GITHUB_TOKEN.toString())
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

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")

    // Network - ApolloClient, OKHTTP, Chuck
    implementation(Libraries.apollo)
    implementation(Libraries.ohttp)
    implementation(Libraries.loggingInterceptor)
    debugImplementation(Libraries.chunkerDebug)
    releaseImplementation(Libraries.chunkerRelease)

    // Coroutines
    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

apollo {
    packageName.set("com.dlight.core")
}