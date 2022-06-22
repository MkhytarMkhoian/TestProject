package com.test.project.features.settings.restaurants

import androidx.datastore.preferences.core.stringPreferencesKey
import com.test.project.features.settings.restaurants.models.RestaurantModel
import com.test.project.shared.storage.DataStorage
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantsDataStorage
@Inject constructor(
  private val moshi: Moshi,
  private val dataStorage: DataStorage
) {

  companion object {
    private val RESTAURANTS_KEY = stringPreferencesKey("RESTAURANTS_KEY")
  }

  suspend fun setRestaurant(model: RestaurantModel) {
    val result = moshi.adapter(RestaurantModel::class.java)
      .serializeNulls()
      .lenient()
      .nullSafe()
      .toJson(model)!!
    dataStorage.put(RESTAURANTS_KEY, result)
  }

  suspend fun getRestaurant(): RestaurantModel? {
    val adapter = moshi.adapter(RestaurantModel::class.java)
      .serializeNulls()
      .lenient()
      .nullSafe()

    val json = dataStorage.get(RESTAURANTS_KEY) ?: return null
    if (json.isEmpty()) return null

    return try {
      adapter.fromJson(json)
    } catch (e: Exception) {
      null
    }
  }

  suspend fun clear() {
    dataStorage.clear()
  }
}
