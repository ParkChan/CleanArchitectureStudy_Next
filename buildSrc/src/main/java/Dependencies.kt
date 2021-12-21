object Version {
    const val gradle = "7.0.3"
    const val kotlin = "1.6.0"
    const val hilt = "2.40.5"
    const val junit5 = "1.8.0.0"

    const val appcompat = "1.4.0"
    const val coreKtx = "1.6.0"
    const val activityKtx = "1.3.1"
    const val fragmentKtx = "1.3.6"
    const val material = "1.1.0"
    const val constraintLayout = "2.1.2"
    const val viewpager2 = "1.0.0"
    const val recyclerView = "1.2.1"
    const val viewModelSavedState = "2.2.0"


    const val coroutine = "1.5.2"

    const val jupiter = "5.8.2"
    const val assertjCore = "3.21.0"
    const val mockk = "1.10.6"
    const val turbine = "0.7.0"
    const val junit = "4.13.2"
    const val extJunit = "1.1.3"
    const val espresso = "3.4.0"

    const val androidTestCore = "1.3.0"
    const val androidTestRunner = "1.3.0"

    const val retrofit = "2.9.0"
    const val moshi = "1.12.0"
    const val loggingInterceptor = "4.8.0"

    const val gson = "2.8.9"
    const val timber = "5.0.1"
    const val glide = "4.12.0"

    const val gradleVersionsPlugin = "0.38.0"

}

object ProjectConfig {
    const val gradle = "com.android.tools.build:gradle:${Version.gradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val androidJunit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Version.junit5}"
    const val hiltAndroidGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt}"
    const val gradleVersionPlugin = "com.github.ben-manes:gradle-versions-plugin:${Version.gradleVersionsPlugin}"
}

object AndroidConfig {
    const val compileSdk = 31
    const val minSdk = 26
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val runnerBuilder = "runnerBuilder"
    const val androidJunitRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val androidJunit5Builder = "de.mannodermaus.junit5.AndroidJUnit5Builder"

}

object CoroutineConfig {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
}

object TestConfig {
    const val junitJupiterApi = "org.junit.jupiter:junit-jupiter-api:${Version.jupiter}"
    const val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Version.jupiter}"
    const val assertjCore = "org.assertj:assertj-core:${Version.assertjCore}"

    const val androidTestCore =
        "de.mannodermaus.junit5:android-test-core:${Version.androidTestCore}"
    const val androidTestRunner =
        "de.mannodermaus.junit5:android-test-runner:${Version.androidTestRunner}"

    const val mockk = "io.mockk:mockk:${Version.mockk}"

    const val turbine = "app.cash.turbine:turbine:${Version.turbine}"

    const val junit = "junit:junit:${Version.junit}"
    const val extJunit = "androidx.test.ext:junit:${Version.extJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"

}

object AndroidXConfig {

    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
    const val material = "com.google.android.material:material:${Version.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"

    const val activityKtx = "androidx.activity:activity-ktx:${Version.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"

    const val viewpager2 = "androidx.viewpager2:viewpager2:${Version.viewpager2}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerView}"

    const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version.viewModelSavedState}"

}

object ViewBindingConfig{
    const val  viewBindingPropertyDelegateNoReflection = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.3"

}

object GoogleConfig {
    const val material = "com.google.android.material:material:${Version.material}"
}

object DaggerHiltConfig {
    const val android = "com.google.dagger:hilt-android:${Version.hilt}"

    const val core = "com.google.dagger:hilt-core:${Version.hilt}"
    const val compiler = "com.google.dagger:hilt-compiler:${Version.hilt}"
}

object NetworkConfig {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Version.loggingInterceptor}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"

    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
    const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Version.moshi}"
}

object LogConfig {
    const val timber = "com.jakewharton.timber:timber:${Version.timber}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Version.glide}"
}

object Gson {
    const val gson = "com.google.code.gson:gson:${Version.gson}"
}