package com.example.ironmind.main

object EserciziRepository {

    val eserciziPerGruppo = mapOf(
        "Petto" to listOf(
            Esercizio("Panca Piana con Bilanciere", "Esercizio base per sviluppare la massa del petto, in particolare la parte centrale"),
            Esercizio("Panca Inclinata con Manubri", "Coinvolge maggiormente la parte alta del petto e i deltoidi anteriori"),
            Esercizio("Panca Decline con Bilanciere", "Focalizza il lavoro sulla parte bassa del petto"),
            Esercizio("Chest Press", "Macchinario utile per lavorare il petto con maggiore stabilità"),
            Esercizio("Croci su Panca Piana con Manubri", "Esercizio di isolamento per l'apertura del petto"),
            Esercizio("Croci ai cavi", "Ottimo per il pompaggio del petto"),
            Esercizio("Dip alle parallele (petto)", "Lavora sul petto basso e tricipiti")
        ),

        "Braccia" to listOf(
            Esercizio("Curl con Bilanciere", "Esercizio base per lo sviluppo dei bicipiti"),
            Esercizio("Curl con Manubri Alternato", "Isola ogni bicipite e migliora la coordinazione"),
            Esercizio("Curl Concentrato", "Per lavorare intensamente un braccio alla volta"),
            Esercizio("French Press con Bilanciere EZ", "Esercizio efficace per i tricipiti"),
            Esercizio("Dip su Panchina", "Tricipiti e parte bassa del petto, utilizzando il peso corporeo"),
            Esercizio("Curl con bilanciere EZ", "Esercizio base per i bicipiti"),
            Esercizio("Curl su panca inclinata", "Stretch del bicipite per maggiore attivazione"),
            Esercizio("Estensioni sopra la testa con manubrio", "Ottimo per colpire il capo lungo del tricipite"),
            Esercizio("Pushdown al cavo con barra", "Esercizio base per il volume dei tricipiti"),
            Esercizio("Curl ai cavi con barra dritta", "Tensione costante durante il movimento"),
            Esercizio("Dip su panca", "Esercizio a corpo libero per tonificare i tricipiti"),
            Esercizio("Kickback con manubrio", "Perfetto per isolare il tricipite")
        ),

        "Spalle" to listOf(
            Esercizio("Shoulder Press con Manubri", "Sviluppa la forza e la massa della spalla"),
            Esercizio("Alzate Laterali", "Isola il deltoide laterale per dare larghezza alla spalla"),
            Esercizio("Alzate Frontali", "Focalizza il lavoro sui deltoidi anteriori"),
            Esercizio("Alzate Posteriori", "Per la parte posteriore della spalla e la parte alta della schiena"),
            Esercizio("Arnold Press", "Variante della shoulder press per attivare più muscoli"),
            Esercizio("Shoulder press alla macchina", "Press guidato per massima stabilità")
        ),

        "Gambe" to listOf(
            Esercizio("Squat con Bilanciere", "Esercizio fondamentale per lo sviluppo di cosce e glutei"),
            Esercizio("Affondi con Manubri", "Lavoro unilaterale per quadricipiti e glutei"),
            Esercizio("Leg Press", "Macchinario per spingere con le gambe in sicurezza"),
            Esercizio("Leg Curl da Sdraiato", "Isola i muscoli posteriori della coscia (femorali)"),
            Esercizio("Leg Extension", "Isola il quadricipite"),
            Esercizio("Stacco da terra con bilanciere", "Coinvolge gambe, glutei e schiena"),
            Esercizio("Hip thrust", "Perfetto per stimolare i glutei"),
            Esercizio("Stacco rumeno con bilanciere", "Allunga e rafforza i femorali"),
            Esercizio("Calf raise in piedi", "Sviluppa la parte superiore del polpaccio"),
            Esercizio("Calf raise alla macchina", "Versione guidata per massima sicurezza"),
            Esercizio("Calf raise su step con manubri", "Ampio range di movimento per la crescita"),
            Esercizio("Kettlebell swing", "Dinamico e ottimo per forza ed esplosività"),
            Esercizio("Good morning con bilanciere", "Allena femorali e zona lombare")
        ),

        "Addominali" to listOf(
            Esercizio("Crunch su Macchina con Carico a Piastra", "Permette di aggiungere carico al movimento classico del crunch"),
            Esercizio("Russian Twist con Palla Medica", "Lavora gli addominali obliqui"),
            Esercizio("Plank", "Ottimo per la stabilità del core"),
            Esercizio("Sollevamento Gambe da Terra", "Coinvolge la parte bassa dell'addome"),
            Esercizio("Bicicletta", "Addominali obliqui e retti con movimento alternato di gambe"),
            Esercizio("Crunch su tappetino", "Esercizio semplice per il retto addominale"),
            Esercizio("Sit-up", "Lavoro completo del busto e del core")
        ),

        "Schiena" to listOf(
            Esercizio("Lat Machine Presa Inversa", "Coinvolge il gran dorsale e il bicipite"),
            Esercizio("Rematore con Bilanciere", "Ottimo per lo sviluppo dello spessore della schiena"),
            Esercizio("Pulley Basso", "Per la parte centrale della schiena"),
            Esercizio("Trazioni alla Sbarra", "Movimento multiarticolare per la schiena e braccia"),
            Esercizio("Stacco da Terra", "Esercizio completo che coinvolge schiena, gambe e core"),
            Esercizio("Lat machine presa larga", "Allarga la schiena e sviluppa il dorsale"),
            Esercizio("Rematore con manubrio", "Focalizza il lavoro su un lato alla volta"),
            Esercizio("Pull-over ai cavi", "Ottimo isolamento per il dorsale")
        ),

        "Core" to listOf(
            Esercizio("Plank frontale", "Migliora la resistenza del core."),
            Esercizio("Side plank", "Rafforza gli obliqui e la stabilità laterale."),
            Esercizio("Ab rollout con ruota", "Esercizio avanzato per addome e stabilizzatori."),
            Esercizio("Dead bug", "Perfetto per il controllo del core."),
            Esercizio("Hollow hold", "Mantiene l’attivazione costante del core.")
        )
    )
}