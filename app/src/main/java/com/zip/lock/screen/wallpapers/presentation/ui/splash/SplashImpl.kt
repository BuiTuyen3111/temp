package com.zip.lock.screen.wallpapers.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leansoft.ads.ui.splash.LeansoftSplashInterface
import com.leansoft.ads.view.BannerAdViewContainer
import com.zip.lock.screen.wallpapers.databinding.FragmentSplashBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashImpl @Inject constructor(): LeansoftSplashInterface {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun getBannerAdContainer(): BannerAdViewContainer {
        return binding.bannerAdContainer
    }
}