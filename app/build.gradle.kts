plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
}

//comportamento configurazione app
android {
    namespace = "com.example.ironmind" //usato per generare il file R (risorse)
    compileSdk = 33 //versione di Android per compilare l' app

    defaultConfig {
        applicationId = "com.example.ironmind" //identificativo univoco della mia app
        minSdk = 26 //versione minima che può installare l'app
        targetSdk = 33 //versione raccomandata per cui l'app è ottimizzata
        versionCode = 1 //numero intero che identifica ogni aggiornamento dell’app
        versionName = "1.0" //versione leggibile dall'utente
    }

    buildTypes {
        release { //configura il tipo di build release per pubblicare l'app
            isMinifyEnabled = false //non attiva l'offuscamento del codice
            proguardFiles( //file usati da ProGuard o R8 per ridurre e proteggere il codice
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 //Java 17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17" //Kotlin compatibile con  Java 17
    }
}

//Tutte le librerie che l'app usa
dependencies {
    implementation("androidx.activity:activity-ktx:1.7.2") //estensioni kotlin per gestire le attività più facilmente
    implementation("androidx.core:core-ktx:1.9.0") //funzionalità kotlin moderne
    implementation("androidx.appcompat:appcompat:1.6.1") //compatibilità con versioni vecchie
    implementation("com.google.android.material:material:1.9.0") //componenti visivi material design
    implementation("com.google.firebase:firebase-auth:22.3.1") //autenticazione firebase
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.code.gson:gson:2.10.1")
}