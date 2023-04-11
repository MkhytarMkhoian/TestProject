package com.test.project.features.player.interactors

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.test.project.shared.interactors.Interactor
import javax.inject.Inject

// TODO add ability to play in background
// TODO think about separate android dependencies from interactor layer
@UnstableApi
class PlayerInteractor @Inject constructor(
  private val context: Context,
) : Interactor {

  private var player: ExoPlayer? = null
  private var mediaItem: MediaItem? = null

  private var playWhenReady = false
  private var currentItem = 0
  private var playbackPosition = 0L

  private fun initializePlayer() {
    val trackSelector = DefaultTrackSelector(context).apply {
      setParameters(buildUponParameters())
    }
    player = ExoPlayer.Builder(context)
      .setTrackSelector(trackSelector)
      .build()
      .also { exoPlayer ->
        exoPlayer.playWhenReady = playWhenReady
        exoPlayer.seekTo(currentItem, playbackPosition)
      }
  }

  private fun releasePlayer() {
    player?.let { exoPlayer ->
      exoPlayer.stop()
      playbackPosition = exoPlayer.currentPosition
      currentItem = exoPlayer.currentMediaItemIndex
      playWhenReady = exoPlayer.playWhenReady
      exoPlayer.release()
    }
    player = null
  }

  fun setMediaItem(url: String) {
    mediaItem = MediaItem.Builder()
      .setUri(url)
      .build()
  }

  fun pause() {
    playWhenReady = false
    player?.pause()
  }

  fun play() {
    mediaItem?.let { mediaItem ->
      player?.setMediaItem(mediaItem)
      player?.prepare()
      player?.play()
      playWhenReady = true
    }
  }

  fun onStart() {
    if (Util.SDK_INT > 23) {
      initializePlayer()
    }
  }

  fun onResume() {
    if (Util.SDK_INT <= 23 || player == null) {
      initializePlayer()
    }
  }

  fun onPause() {
    if (Util.SDK_INT <= 23) {
      releasePlayer()
    }
  }

  fun onStop() {
    if (Util.SDK_INT > 23) {
      releasePlayer()
    }
  }
}

