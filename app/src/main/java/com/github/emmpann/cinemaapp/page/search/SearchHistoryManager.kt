package com.github.emmpann.cinemaapp.page.search

import android.content.Context

object SearchHistoryManager {
    private const val PREFS_NAME = "SearchHistoryPrefs"
    private const val HISTORY_KEY = "search_history"

    fun saveSearchQuery(context: Context, query: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val history = getSearchHistory(context).toMutableSet()
        history.add(query)
        prefs.edit().putStringSet(HISTORY_KEY, history).apply()
    }

    fun getSearchHistory(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(HISTORY_KEY, setOf()) ?: setOf()
    }

    fun clearSearchHistory(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(HISTORY_KEY).apply()
    }
}