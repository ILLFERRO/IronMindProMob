//configuro il progetto a livello globale
pluginManagement { //mi dice dove trovo i miei plugin
    repositories {
        google() // Plugin ufficiali di Google (es. Android)
        gradlePluginPortal() // Plugin pubblici da plugins.gradle.org
        mavenCentral() // Repository Maven standard
    }
}

dependencyResolutionManagement { //qui mi indica dove cerco le mie librerie usate nei miei moduli
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) //blocca ogni repositories {} dentro i singoli moduli, obbliga il progetto a usare solo questi repository globali per maggiore sicurezza e controllo
    repositories { //mi indica dove cerco le mie dipendenze
        google() //dipendenze Android ufficiali
        mavenCentral() //librerie Java/Kotlin generiche
    }
}

rootProject.name = "IronMind" //imposta il nome del progetto root
include(":app") //include il modulo app nel progetto