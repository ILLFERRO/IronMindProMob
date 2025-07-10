package com.example.ironmind.Activity

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
            val app = getApplication<Application>()

            root.keys().forEach { prefName ->
                val prefs   = app.getSharedPreferences(prefName, Context.MODE_PRIVATE)
                val editor  = prefs.edit()
                editor.clear()
                val block   = root.getJSONObject(prefName)

                block.keys().forEach { key ->
                    val v = block.get(key)

                    when (v) {
                        is Int     -> editor.putInt(key, v)
                        is Boolean -> editor.putBoolean(key, v)
                        is Float   -> editor.putFloat(key, v)
                        is Long    -> editor.putLong(key, v)

                        is String  -> {
                            val s = v.trim()
                            if (s.startsWith("[") && s.endsWith("]")) {
                                val arr = JSONArray(s)
                                val set = mutableSetOf<String>()
                                for (i in 0 until arr.length()) set += arr.getString(i)
                                editor.putStringSet(key, set)
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