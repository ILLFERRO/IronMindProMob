package com.example.ironmind

import com.example.ironmind.main.Esercizio //importa classe modello Esercizio
import com.example.ironmind.main.SchedaManager //importa SchedaManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SchedaManagerTest {


    private lateinit var esercizio: Esercizio //variabile che viene inizializzata prima di ogni test con oggetto Esercizio
    private val nomeScheda = "TestScheda" //nome della scheda fittizia da utilizzare per testare (costante)

    @Before //fa eseguire la funzione setup prima di ogni test
    fun setup() {
        // Reset della scheda prima di ogni test
        SchedaManager.schedePersonalizzate.clear() //svuoto la cache di SchedaManager per evitare interferenze nei test

        esercizio = Esercizio( //creo un esercizio
            nome = "Panca Piana",
            descrizione = "Esercizio per il petto",
            setPrevisti = 3,
            ripetizioniPreviste = 10,
            usaPeso = true,
            pesoPredefinito = 40f
        )
    }

    @Test //test per aggiungere correttamente un esercizio alla scheda
    fun `aggiungiEsercizio aggiunge correttamente alla scheda`() {
        SchedaManager.aggiungiEsercizio(nomeScheda, esercizio)

        val lista = SchedaManager.getScheda(nomeScheda) //recupero la la lista di esercizi
        assertEquals(1, lista.size) //controllo con assertEquals che la lista abbia esattamente 1 elemento
        assertEquals("Panca Piana", lista[0].nome) //controlla che il nome dell'esercizio aggiunto sia giusto
    }

    @Test
    fun `getScheda restituisce lista corretta`() { //testa che si possano aggiungere più esercizi e che mi restituisca la lista corretta e completa
        SchedaManager.aggiungiEsercizio(nomeScheda, esercizio) //qui aggiungo Panca Piana
        SchedaManager.aggiungiEsercizio(nomeScheda, esercizio.copy(nome = "Lat Machine")) //qui faccio uguale tramite .copy e aggiungo Lat Machine

        val lista = SchedaManager.getScheda(nomeScheda)
        assertEquals(2, lista.size) //controlla che la lista contenga esattamente 2 esercizi
        assertTrue(lista.any { it.nome == "Panca Piana" })
        assertTrue(lista.any { it.nome == "Lat Machine" }) //controlla i nomi degli esercizi che contiene la lista
    }
}