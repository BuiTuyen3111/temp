package com.zip.lock.screen.wallpapers.presentation.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.recyclerview.widget.RecyclerView
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.databinding.ItemVideoBinding
import com.zip.lock.screen.wallpapers.ext.gone
import com.zip.lock.screen.wallpapers.ext.visible
import com.zip.lock.screen.wallpapers.presentation.ui.home.HomeVM
import com.zip.lock.screen.wallpapers.utils.VideoCache
import com.zip.lock.screen.wallpapers.utils.safeOnClickListener

@UnstableApi
class VideoPagerAdapter(
    private var viewModel: HomeVM,
    private val videos: List<String>,
    var onFavorite: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<VideoPagerAdapter.VideoVH>() {
    private val activePlayers = mutableListOf<ExoPlayer>()
    inner class VideoVH(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        var player: ExoPlayer? = null

        fun updateUiState(isPlay: Boolean) {
            binding.apply {
                if (isPlay) {
                    btnPlayCircle.gone()
                    tvPlay.text = root.context.resources.getString(R.string.msg_pause)
                    ivPlay.isSelected = false
                } else {
                    btnPlayCircle.visible()
                    tvPlay.text = root.context.resources.getString(R.string.msg_play)
                    ivPlay.isSelected = true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVH {
        return VideoVH(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: VideoVH, position: Int) {

        val ctx = holder.itemView.context
        val url = videos[position]

        holder.player?.let {
            activePlayers.remove(it)
            it.release()
        }

        val player = ExoPlayer.Builder(ctx).build()
        activePlayers.add(player)
        holder.player = player
        holder.binding.playerView.player = player

        val mediaItem = MediaItem.fromUri(url)
        val cacheFactory = CacheDataSource.Factory()
            .setCache(VideoCache.getInstance(ctx))
            .setUpstreamDataSourceFactory(DefaultDataSource.Factory(ctx))
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

        val mediaSource = ProgressiveMediaSource.Factory(cacheFactory)
            .createMediaSource(mediaItem)
        player.setMediaSource(mediaSource)
        player.repeatMode = Player.REPEAT_MODE_ALL
        player.prepare()
        player.playWhenReady = true
        holder.binding.apply {
            btnLike.isSelected = viewModel.isFavorite(url)
            btnLike.safeOnClickListener {
                btnLike.isSelected = !btnLike.isSelected
                onFavorite.invoke(position, btnLike.isSelected)
            }
            btnPlayCircle.safeOnClickListener {
                if (!player.isPlaying) {
                    player.play()
                    holder.updateUiState(true)
                }
            }
            btnPlayOrPause.safeOnClickListener {
                if (player.isPlaying) {
                    player.pause()
                    holder.updateUiState(false)
                } else {
                    player.play()
                    holder.updateUiState(true)
                }
                btnPlayCircle.isSelected = player.isPlaying
            }
            btnReplay.safeOnClickListener {
                player.seekTo(0)
                player.play()
                holder.updateUiState(true)
            }
        }
    }

    override fun onViewRecycled(holder: VideoVH) {
        holder.binding.playerView.player = null
        holder.player?.let {
            activePlayers.remove(it)
            it.release()
        }
        holder.player = null
    }

    override fun getItemCount() = videos.size

    fun releaseAllPlayers() {
        for (p in activePlayers) {
            p.release()
        }
        activePlayers.clear()
    }

    fun pauseAllPlayers() {
        for (p in activePlayers) {
            p.pause()
        }
    }
}

