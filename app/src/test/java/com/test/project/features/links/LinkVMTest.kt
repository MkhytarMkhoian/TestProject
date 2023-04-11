package com.test.project.features.links

import app.cash.turbine.test
import com.test.project.NavAppDirections
import com.test.project.app.navigation.AppNavigator
import com.test.project.features.links.interactors.LinkInteractor
import com.test.project.features.links.models.LinkModel
import com.test.project.features.links.models.LinkType
import com.test.project.features.links.models.LinksResponse
import com.test.project.features.links.models.StationModel
import com.test.project.features.player.models.PlayerBundle
import com.test.project.shared.api.AppApiRepository
import com.test.project.shared.api.ex.BaseApiException
import com.test.project.shared.api.ex.NetworkException
import com.test.project.shared.api.ex.UnexpectedException
import com.test.project.shared.coroutines.CoroutineDispatchersTest
import com.test.project.shared.extensions.generateId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.*

// TODO covered only this view model as for example and skiped others for the sake of time
// TODO covert other view models

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(value = [TestCoroutineExtension::class])
internal class LinkVMTest {

  companion object {
    private const val DEFAULT_LINK = "https://opml.radiotime.com/" // default page
  }

  private lateinit var viewModel: LinkVM

  private val homePage: LinksResponse = homePage()
  private val appApiRepository = mock<AppApiRepository> {
    onBlocking { getLinks(DEFAULT_LINK) } doReturn homePage
  }
  private val linkInteractor = spy(
    LinkInteractor(
      appApiRepository = appApiRepository,
      coroutineDispatchers = CoroutineDispatchersTest()
    )
  )

  private val appNavigator = mock<AppNavigator>()

  private fun setUp(
    appNavigator: AppNavigator = this.appNavigator,
    linkInteractor: LinkInteractor = this.linkInteractor,
  ) {
    viewModel = spy(
      LinkVM(
        appNavigator = appNavigator,
        linkInteractor = linkInteractor,
      )
    )
  }

  @Test
  @DisplayName("Given successful load When call loadLinks() method Then set correct state")
  fun loadLinks() = runTest {
    // Given
    setUp()
    viewModel.setUrl(DEFAULT_LINK)

    // When
    launch {
      viewModel.isLoading.test {
        assertFalse(awaitItem())
        assertTrue(awaitItem())
        assertFalse(awaitItem())
      }
    }

    launch {
      viewModel.loadLinks()
    }.join()

    // Then
    verify(linkInteractor).getLinks(DEFAULT_LINK)

    assertEquals(false, viewModel.errorState.value.showUnknownErrorMessage)
    assertEquals(false, viewModel.errorState.value.showNetworkErrorMessage)

    assertEquals(homePage.head.title, viewModel.title.value)

    viewModel.items.value.forEachIndexed { index, item ->
      val actual = item as LinkModel
      val expected = homePage.body[index]
      kotlin.test.assertEquals(expected.text, actual.text)
      kotlin.test.assertEquals(expected.type, actual.type.id)
      kotlin.test.assertEquals(expected.url, actual.url)
      kotlin.test.assertEquals(expected.element, actual.element)
      kotlin.test.assertEquals(expected.key, actual.key?.id)
    }
  }

  @Test
  @DisplayName("Given response with NetworkException When call loadLinks() method Then show correct error state")
  fun loadLinksWithNetworkException() = runTest {
    val e = NetworkException("NetworkException", Exception())

    loadDataWithException(e)
  }

  @Test
  @DisplayName("Given response with different BaseApiException NetworkException from  When call loadLinks() method Then show correct error state")
  fun loadLinksWithNotNetworkException() = runTest {
    val e = UnexpectedException("UnexpectedException", Exception(), 406)

    loadDataWithException(e)
  }

  private suspend fun loadDataWithException(e: BaseApiException) = runTest {
    // Given
    val linkInteractor = mock<LinkInteractor> {
      onBlocking { getLinks(DEFAULT_LINK) } doThrow e
    }
    setUp(linkInteractor = linkInteractor)
    viewModel.setUrl(DEFAULT_LINK)

    // When
    launch {
      viewModel.isLoading.test {
        assertFalse(awaitItem())
        assertTrue(awaitItem())
        assertFalse(awaitItem())
      }
    }

    launch {
      viewModel.loadLinks()
    }.join()

    // Then
    assertEquals("", viewModel.title.value)
    assertTrue(viewModel.items.value.isEmpty())

    if (e is NetworkException) {
      assertEquals(false, viewModel.errorState.value.showUnknownErrorMessage)
      assertEquals(true, viewModel.errorState.value.showNetworkErrorMessage)

    } else {
      assertEquals(true, viewModel.errorState.value.showUnknownErrorMessage)
      assertEquals(false, viewModel.errorState.value.showNetworkErrorMessage)
    }
  }

  @Test
  fun onLinkClick() = runTest {
    // Given
    setUp()

    val item = LinkModel(
      id = generateId(),
      text = "text",
      url = "url",
      element = "element",
      type = LinkType.Link,
      key = null,
    )

    // When
    viewModel.onLinkClick(item)

    // Then
    verify(appNavigator).navigate(
      NavAppDirections.navActionLinks(item.url)
    )
  }

  @Test
  fun onStationLinkClick() = runTest {
    // Given
    setUp()

    val item = StationModel(
      id = generateId(),
      text = "text",
      subtext = "subtext",
      url = "url",
      image = "image",
      type = LinkType.Link,
    )

    // When
    viewModel.onLinkClick(item)

    // Then
    verify(appNavigator).navigate(
      LinkFragmentDirections.navActionPlayer(PlayerBundle(item))
    )
  }

  @Test
  fun onBackPressed() {
    // Given
    setUp()

    // When
    viewModel.onBackPressed()

    // Then
    verify(appNavigator).popBackStack()
  }

  @Test
  fun setUrl() {
    setUrl("url")
    setUrl(null)
  }

  private fun setUrl(url: String?) {
    // Given
    setUp()

    // When
    viewModel.setUrl(url)

    // Then
    assertEquals(viewModel.showBackButton.value, !url.isNullOrEmpty())
  }
}