plugins {
    id("com.android.application") version "8.0.2" apply false //per costruire app Android
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false //per usare Kotlin nelle app Android
    id("com.google.gms.google-services") version "4.3.15" apply false //necessario per usare Firebase, legge il file google-services.json
}
// qui dichiaro i plugins ma non li applico subito: apply false

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
// Registra un task personalizzato chiamato clean
//Task di tipo delete, modo lazy e moderno di definire un task
//Specifica cosa deve essere eliminato quando esegui ./gradlew clean, in questo caso elimina la cartella build/ del progetto root