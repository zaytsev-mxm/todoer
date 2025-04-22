package dev.maxiscoding.todoer.services

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.maxiscoding.todoer.TOKEN_PREF
import dev.maxiscoding.todoer.debug.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var dataStore = context.dataStore

    fun getTokenPrefFlow(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[TOKEN_PREF] }
            .distinctUntilChanged()
    }

    suspend fun updateTokenPref(token: String?) {
        dataStore.edit { settings ->
            if (token == null) {
                settings.remove(TOKEN_PREF)
            } else {
                settings[TOKEN_PREF] = token
            }
        }
    }
}