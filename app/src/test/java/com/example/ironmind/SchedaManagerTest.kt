package com.example.ironmind

import com.example.ironmind.main.Esercizio
import com.example.ironmind.main.SchedaManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SchedaManagerTest {


    private lateinit var esercizio: Esercizio
    private val nomeScheda = "TestScheda"

    @Before
    fun setup() {
        // Reset della scheda prima di ogni test
        SchedaManager.schedePersonalizzate.clear()

        esercizio = Esercizio(
            nome = "Panca Piana",
            descrizione = "Esercizio per il petto",
            setPrevisti = 3,
            ripetizioniPreviste = 10,
            usaPeso = true,
            pesoPredefinito = 40f
        )
    }

    @Test
    fun `aggiungiEsercizio aggiunge correttamente alla scheda`() {
        SchedaManager.aggiungiEsercizio(nomeScheda, esercizio)

        val lista = SchedaManager.getScheda(nomeScheda)
        assertEquals(1, lista.size)
        assertEquals("Panca Piana", lista[0].nome)
    }

    @Test
    fun `getScheda restituisce lista corretta`() {
        SchedaManager.aggiungiEsercizio(nomeScheda, esercizio)
        SchedaManager.aggiungiEsercizio(nomeScheda, esercizio.copy(nome = "Lat Machine"))

        val lista = SchedaManager.getScheda(nomeScheda)
        assertEquals(2, lista.size)
        assertTrue(lista.any { it.nome == "Panca Piana" })
        assertTrue(lista.any { it.nome == "Lat Machine" })
    }
}