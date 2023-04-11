package com.test.project.features.links.interactors

import com.test.project.features.links.TestCoroutineExtension
import com.test.project.features.links.homePage
import com.test.project.features.links.models.LinkModel
import com.test.project.features.links.models.LinksResponse
import com.test.project.shared.api.AppApiRepository
import com.test.project.shared.coroutines.CoroutineDispatchersTest
import com.test.project.shared.models.LinkEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

// TODO covered only this interactor as for example and skiped others for the sake of time
// TODO covert other interactors

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(value = [TestCoroutineExtension::class])
internal class LinkInteractorTest {

  companion object {
    private const val DEFAULT_LINK = "https://opml.radiotime.com/" // default page
  }

  private lateinit var linkInteractor: LinkInteractor

  private val homePage: LinksResponse = homePage()
  private val appApiRepository = mock<AppApiRepository> {
    onBlocking { getLinks(DEFAULT_LINK) } doReturn homePage
  }

  private fun setUp(
    appApiRepository: AppApiRepository = this.appApiRepository,
  ) {
    linkInteractor = spy(
      LinkInteractor(
        appApiRepository = appApiRepository,
        coroutineDispatchers = CoroutineDispatchersTest()
      )
    )
  }

  @Test
  @DisplayName("Given successful response When call getLinks() method Then return correctly mapped data")
  fun getLinks() = runTest {
    // Given
    setUp()

    // When
    val data = linkInteractor.getLinks(DEFAULT_LINK)

    // Then
    verify(appApiRepository).getLinks(DEFAULT_LINK)

    assertEquals(homePage.head.title, data.title)

    data.feedItems.forEachIndexed { index, item ->
      val actual = item as LinkModel
      val expected = homePage.body[index]
      assertEquals(expected.text, actual.text)
      assertEquals(expected.type, actual.type.id)
      assertEquals(expected.url, actual.url)
      assertEquals(expected.element, actual.element)
      assertEquals(expected.key, actual.key?.id)
    }
  }

}