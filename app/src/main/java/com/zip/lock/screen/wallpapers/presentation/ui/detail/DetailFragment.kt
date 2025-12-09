package com.zip.lock.screen.wallpapers.presentation.ui.detail

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.zip.lock.screen.wallpapers.data.source.local.database.enitities.VideoEntity
import com.zip.lock.screen.wallpapers.databinding.FragmentDetailBinding
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseFragment
import com.zip.lock.screen.wallpapers.presentation.ui.base.NothingViewModel
import com.zip.lock.screen.wallpapers.utils.safeOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@UnstableApi
@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding, NothingViewModel>() {

    private val args: DetailFragmentArgs by navArgs()

    private var currPlayer: ExoPlayer? = null
    private var adapter: VideoPagerAdapter? = null
    private var job: Job? = null
    private var data = emptyList<VideoEntity>()

    override fun getClassVM(): Class<NothingViewModel> {
        return NothingViewModel::class.java
    }

    override fun initViewBinding(): FragmentDetailBinding {
       return FragmentDetailBinding.inflate(layoutInflater)
    }

    override fun initView() {
        register()
        data = if (args.category != "favorite") {
            homeVM.map[args.category] ?: emptyList()
        } else {
            homeVM.favoriteList.value
        }
        setUpPager(data.map { it.path })
    }

    private fun register() {
        with(mBinding) {
            btnBack.safeOnClickListener {
                navigationViewModel.back()
            }
        }
    }

    private fun setUpPager(feeds: List<String>) {
        adapter = VideoPagerAdapter(homeVM, feeds) { pos, isFavorite ->
            if (data.size > pos) homeVM.toggleFavorite(data[pos], isFavorite)
        }
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(500)
                    playVideoAt(position)
                }
                job?.start()
            }
        })
        if (feeds.size > args.position) {
            mBinding.viewPager.setCurrentItem(args.position, false)
        }
    }

    private fun playVideoAt(position: Int) {
        if (!isAdded) return
        currPlayer?.playWhenReady = false
        val recyclerView = mBinding.viewPager.getChildAt(0) as RecyclerView
        val holder = recyclerView.findViewHolderForAdapterPosition(position)
                as? VideoPagerAdapter.VideoVH ?: return
        val player = holder.binding.playerView.player as? ExoPlayer ?: return
        player.prepare()
        player.playWhenReady = true
        holder.updateUiState(true)
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    if (!isAdded) return

                } else if (state == Player.STATE_IDLE || state == Player.STATE_BUFFERING) {
                    if (!isAdded) return
                }
            }
            override fun onPlayerError(error: PlaybackException) {
                Log.e("", "Video $position error: ${error.errorCodeName}")
            }
        })
        currPlayer = player
    }

    override fun onPause() {
        super.onPause()
        currPlayer?.pause()
        adapter?.pauseAllPlayers()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300)
            if (!isAdded) return@launch
            if (activity?.supportFragmentManager?.findFragmentByTag("NativeFullDialogFragment") == null) {
                currPlayer?.play()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        currPlayer?.release()
        adapter?.releaseAllPlayers()
    }
}