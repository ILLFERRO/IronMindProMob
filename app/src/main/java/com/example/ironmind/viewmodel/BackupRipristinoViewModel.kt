package com.example.ironmind.viewmodel

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import org.json.JSONArray
import org.json.JSONObject

class BackupRipristinoViewModel(application: Application) : AndroidViewModel(application) {

    fun creaBackupJson(): String {
        val context = getApplication<Application>()
        val allPrefsNames = listOf("IronMindPrefs", "settings", "allenamento_stats", "premi_sbloccati")
        val rootJson = JSONObject()

        for (prefName in allPrefsNames) {
            val prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            val all = prefs.all
            val json = JSONObject()

            for ((key, value) in all) {
                when (value) {
                    is Set<*> -> {
                        val jsonArray = org.json.JSONArray()
                        value.forEach { v -> jsonArray.put(v) }
                        json.put(key, jsonArray.toString())
                    }
                    else -> json.put(key, value)
                }
            }

            rootJson.put(prefName, json)
        }

        return rootJson.toString()
    }

    fun salvaBackupSuUri(uri: Uri, contentResolver: ContentResolver, onResult: (success: Boolean) -> Unit) {
        val json = creaBackupJson()
        try {
            contentResolver.openOutputStream(uri)?.use { output ->
                output.write(json.toByteArray())
                output.flush()
            } ?: throw Exception("OutputStream null")
            onResult(true)
        } catch (e: Exception) {
            onResult(false)
        }
    }

    fun ripristinaBackupDaUri(
        uri: Uri,
        contentResolver: ContentResolver,
        onResult: (Boolean) -> Unit
    ) {
        try {
            val raw = contentResolver.openInputStream(uri)?.use { it.reader().readText() }
            if (raw.isNullOrEmpty()) return onResult(false)

            val root = JSONObject(raw)
            val app  = getApplication<Application>()

            root.keys().forEach { prefName ->
                val prefs  = app.getSharedPreferences(prefName, Context.MODE_PRIVATE)
                val editor = prefs.edit().clear()

                val block  = root.getJSONObject(prefName)
                block.keys().forEach { key ->
                    val v = block.get(key)

                    when (v) {
                        is Int -> {
                            val toLong  = key.startsWith("durata_") || key.endsWith("_sec")
                            val toFloat = key.contains("peso")      || key.endsWith("_totale")

                            when {
                                toLong  -> editor.putLong(key,  v.toLong())
                                toFloat -> editor.putFloat(key, v.toFloat())
                                else    -> editor.putInt(key,   v)
                            }
                        }
                        is Long   -> editor.putLong(key, v)
                        is Double -> editor.putFloat(key, v.toFloat())
                        is Float  -> editor.putFloat(key, v)
                        is Boolean-> editor.putBoolean(key, v)

                        is String -> {
                            val trim = v.trim()

                            val isSchedaJson = key.startsWith("scheda_")

                            if (!isSchedaJson && trim.startsWith("[") && trim.endsWith("]")) {
                                try {
                                    val arr = JSONArray(trim)
                                    val asSet = (0 until arr.length())
                                        .map { arr.getString(it) }
                                        .toMutableSet()
                                    editor.putStringSet(key, asSet)
                                } catch (_: Exception) {
                                    editor.putString(key, v)
                                }
                            } else {
                                editor.putString(key, v)
                            }
                        }

                        else -> editor.putString(key, v.toString())
                    }
                }
                editor.apply()
            }

            onResult(true)
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(false)
        }
    }
}