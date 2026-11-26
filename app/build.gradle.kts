plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.livemachine.academy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.livemachine.academy"
        minSdk = 26
        targetSdk = 35
        versionCode = 14
        versionName = "1.2.0"
    }

    // Clé release fournie par variables d'environnement (voir README). Sans elles,
    // la release est signée avec la clé debug : installable, mais pas publiable sur un store.
    val releaseKeystore = System.getenv("LMA_KEYSTORE_PATH")?.let { file(it) }?.takeIf { it.exists() }
    signingConfigs {
        // Clé partagée versionnée (non secrète, comme une clé debug) : toutes les compilations,
        // sur n'importe quel runner, ont la même signature, donc s'installent par-dessus.
        getByName("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        if (releaseKeystore != null) {
            create("release") {
                storeFile = releaseKeystore
                storePassword = System.getenv("LMA_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("LMA_KEY_ALIAS")
                keyPassword = System.getenv("LMA_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.findByName("release") ?: signingConfigs.getByName("debug")
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
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.exifinterface)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
}
