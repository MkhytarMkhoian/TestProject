package com.test.project.shared.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface DataStorage {

  suspend fun <T> put(key: Preferences.Key<T>, value: T)

  suspend fun <T> get(key: Preferences.Key<T>): T?

  suspend fun <T> remove(key: Preferences.Key<T>)

  suspend fun <T> hasKey(key: Preferences.Key<T>): Boolean

  suspend fun clear()
}

@Singleton
class DataStorageImpl
@Inject constructor(
  context: Context
) : DataStorage {

  companion object {
    const val STORE_NAME = "allset_merchant_data_store"
  }

  private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)

  private val dataStore: DataStore<Preferences> = context.preferencesDataStore

  override suspend fun <T> put(key: Preferences.Key<T>, value: T) {
    dataStore.edit { preferences ->
      preferences[key] = value
    }
  }

  private suspend fun getPreferences(): Preferences {
    return try {
      dataStore.data.first()
    } catch (e: IOException) {
      emptyPreferences()
    } catch (e: Exception) {
      throw e
    }
  }

  override suspend fun <T> get(key: Preferences.Key<T>): T? {
    return try {
      val result = getPreferences()[key]
      result
    } catch (e: Exception) {
      null
    }
  }

  override suspend fun <T> remove(key: Preferences.Key<T>) {
    dataStore.edit { preferences ->
      preferences.remove(key)
    }
  }

  override suspend fun <T> hasKey(key: Preferences.Key<T>): Boolean {
    return getPreferences().contains(key)
  }

  override suspend fun clear() {
    dataStore.edit { preferences ->
      preferences.clear()
    }
  }
}
