package com.test.project.features.links.interactors

import com.test.project.features.links.models.*
import com.test.project.shared.api.AppApiRepository
import com.test.project.shared.coroutines.CoroutineDispatchers
import com.test.project.shared.extensions.generateId
import com.test.project.shared.interactors.Interactor
import com.test.project.shared.models.FeedItem
import com.test.project.shared.models.LinkEntity
import com.test.project.shared.models.StationEntity
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LinkInteractor @Inject constructor(
  private val appApiRepository: AppApiRepository,
  private val coroutineDispatchers: CoroutineDispatchers,
) : Interactor {

  companion object {
    private const val DEFAULT_LINK = "https://opml.radiotime.com/" // default page
  }

  suspend fun getLinks(url: String?) =
    withContext(coroutineDispatchers.defaultContext) {
      val response = appApiRepository.getLinks(if (url.isNullOrEmpty()) DEFAULT_LINK else url)
      ScreenData(
        title = response.head.title ?: "",
        feedItems = mapLinks(response.body),
      )
    }

  private fun mapLinks(items: List<LinkEntity>): List<FeedItem> {
    return items.flatMap { item ->
      val list = mutableListOf<FeedItem>()
      if (!item.children.isNullOrEmpty()) {
        list.add(
          HeaderModel(
            id = generateId(),
            text = item.text ?: "",
          )
        )

        val stations = item.children.map { child ->
          val type = LinkType.from(child.type)
          mapStationEntity(type, child)
        }

        list.addAll(stations)
      } else {
        val type = LinkType.from(item.type)
        list.add(
          mapLinkEntity(type, item)
        )
      }

      list
    }
  }

  private fun mapStationEntity(type: LinkType, item: StationEntity): FeedItem {
    return if (type == LinkType.Audio) {
      StationModel(
        id = item.url.hashCode().toLong(),
        text = item.text,
        subtext = item.subtext ?: "",
        url = item.url,
        image = item.image ?: "",
        type = type,
      )
    } else {
      LinkModel(
        id = generateId(),
        element = item.element,
        type = type,
        text = item.text,
        url = item.url,
        key = StationType.None,
      )
    }
  }

  private fun mapLinkEntity(type: LinkType, item: LinkEntity): FeedItem {
    return if (type == LinkType.Audio) {
      StationModel(
        id = item.url.hashCode().toLong(),
        text = item.text ?: "",
        subtext = "",
        url = item.url ?: "",
        image = "",
        type = type,
      )
    } else {
      LinkModel(
        id = generateId(),
        element = item.element,
        type = LinkType.from(item.type),
        text = item.text ?: "",
        url = item.url ?: "",
        key = StationType.from(item.key),
      )
    }
  }
}

