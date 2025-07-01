package com.example.ironmind

import com.example.ironmind.utils.formattaRecupero
import com.example.ironmind.main.toCleanFloat
import org.junit.Assert.assertEquals //funzione per eseguire verifiche nei test
import org.junit.Test //serve per indicare i metodi di test

class AllenamentoDinamicoUITest {

    @Test
    fun `toCleanFloat rimuove testo superfluo e converte correttamente`() { //prende le stringhe con testo superfluo
        assertEquals(42.5f, "42,5 kg".toCleanFloat()) //usa assertEquals per controllare che il valore restituito corrisponda al valore atteso
        assertEquals(50.0f, "50 kg".toCleanFloat())
        assertEquals(30.2f, "30.2".toCleanFloat())
    }

    @Test
    fun `formattaRecupero converte correttamente i secondi`() { //Verifica che il formato restituito sia corretto, ad esempio 90 secondi diventano Recupero: 01:30.
        assertEquals("Recupero: 01:30", formattaRecupero(90)) //usa assertEquals per controllare che il valore restituito corrisponda al valore atteso
        assertEquals("Recupero: 00:45", formattaRecupero(45))
        assertEquals("Recupero: 10:00", formattaRecupero(600))
    }
}