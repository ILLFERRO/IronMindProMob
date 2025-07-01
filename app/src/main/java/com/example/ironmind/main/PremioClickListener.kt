package com.example.ironmind.main

interface PremioClickListener { //definisco interfaccia PremioClickListener che serve a gestire click su un oggetto Premio
    fun onPremioClicked(premio: Premio)
}