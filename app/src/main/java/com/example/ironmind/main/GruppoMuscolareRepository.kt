package com.example.ironmind.main

import com.example.ironmind.R

object GruppiMuscolariRepository {
    val listaGruppi = listOf(
        GruppoMuscolare("Petto", "Esercizi per sviluppare i muscoli pettorali", R.drawable.immagine_petto),
        GruppoMuscolare("Braccia", "Esercizi per bicipiti e tricipiti", R.drawable.immagine_braccia),
        GruppoMuscolare("Spalle", "Esercizi per le spalle", R.drawable.immagine_spalle),
        GruppoMuscolare("Gambe", "Esercizi per quadricipiti, femorali e polpacci", R.drawable.immagine_gambe),
        GruppoMuscolare("Addominali", "Esercizi per la zona addominale", R.drawable.immagine_addominali),
        GruppoMuscolare("Schiena", "Esercizi per dorsali e lombari", R.drawable.immagine_schiena),
        GruppoMuscolare("Core", "Esercizi per la stabilizzazione e il controllo del corpo", R.drawable.immagine_core)
    )
}
