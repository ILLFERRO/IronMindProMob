package com.example.ironmind

import com.example.ironmind.Utils.formattaRecupero
import com.example.ironmind.view.toCleanFloat
import org.junit.Assert.assertEquals
import org.junit.Test

class AllenamentoDinamicoUITest {

    @Test
    fun `toCleanFloat rimuove testo superfluo e converte correttamente`() {
        assertEquals(42.5f, "42,5 kg".toCleanFloat())
        assertEquals(50.0f, "50 kg".toCleanFloat())
        assertEquals(30.2f, "30.2".toCleanFloat())
    }

    @Test
    fun `formattaRecupero converte correttamente i secondi`() {
        assertEquals("Recupero: 01:30", formattaRecupero(90))
        assertEquals("Recupero: 00:45", formattaRecupero(45))
        assertEquals("Recupero: 10:00", formattaRecupero(600))
    }
}