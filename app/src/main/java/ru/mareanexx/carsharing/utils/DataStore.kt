package ru.mareanexx.carsharing.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "userPrefs")
        private val ID_USER = intPreferencesKey(name = "id_user")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val userId : Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[ID_USER] ?: -1 }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[IS_LOGGED_IN] ?: false }

    suspend fun saveUser(isLoggedIn: Boolean, idUser: Int) {
        context.dataStore.edit {
            preferences ->
            Log.d("STORE", "Trying to save user with idUser = $idUser and $isLoggedIn")
            preferences[IS_LOGGED_IN] = isLoggedIn
            preferences[ID_USER] = idUser
        }
    }

    suspend fun clearUser() {
        context.dataStore.edit {
            it.clear()
        }
    }


}