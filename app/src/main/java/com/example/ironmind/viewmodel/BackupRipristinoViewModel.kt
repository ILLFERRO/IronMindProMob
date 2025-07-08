package com.example.ironmind.Activity

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import org.json.JSONObject

class BackupRipristinoViewModel(application: Application) : AndroidViewModel(application) {

    fun creaBackupJson(): String {
        val prefs = getApplication<Application>().getSharedPreferences("IronMindPrefs", 0)
        val allPrefs = prefs.all
        return JSONObject(allPrefs).toString()
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

    fun ripristinaBackupDaUri(uri: Uri, contentResolver: ContentResolver, onResult: (success: Boolean) -> Unit) {
        try {
            val input = contentResolver.openInputStream(uri)?.use { it.reader().readText() }
            if (input != null) {
                val prefs = getApplication<Application>().getSharedPreferences("IronMindPrefs", 0)
                val json = JSONObject(input)
                val editor = prefs.edit()
                json.keys().forEach { key ->
                    when (val value = json.get(key)) {
                        is Int -> editor.putInt(key, value)
                        is Boolean -> editor.putBoolean(key, value)
                        is Float -> editor.putFloat(key, value)
                        is Long -> editor.putLong(key, value)
                        is String -> editor.putString(key, value)
                        else -> {}
                    }
                }
                editor.apply()
                onResult(true)
            } else {
                onResult(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(false)
        }
    }
}