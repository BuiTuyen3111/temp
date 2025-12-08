package com.zip.lock.screen.wallpapers.presentation.ui.uninstall

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leansoft.ads.ui.uninstall.LeansoftUninstallInterface
import com.leansoft.ads.view.NativeAdViewContainer
import com.zip.lock.screen.wallpapers.databinding.FragmentUninstallBinding
import javax.inject.Singleton

@Singleton
class UninstallAppImpl : LeansoftUninstallInterface {

    private lateinit var binding: FragmentUninstallBinding
    private var listener:((Bundle)-> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUninstallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    override fun getNativeAdViewContainer(): NativeAdViewContainer {
        return binding.nativeDefault
    }

    override fun registerGoToMainListener(listener: (Bundle) -> Unit) {
        this.listener = listener
    }
}