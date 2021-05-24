package com.example.myapplication

import android.content.Context

class SharedPreferencesManager(private val context: Context) {

    private val sharedPreferencesManager = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun writeString(key: String, value: String) {
        sharedPreferencesManager.edit().putString(key, value).apply()
    }

    fun readString(key: String) = sharedPreferencesManager.getString(key, "")

    companion object {
        private const val SHARED_PREFERENCES = "NOTE_APP"
        const val NOTES = "NOTES"
    }
}