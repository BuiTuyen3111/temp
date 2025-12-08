package com.zip.lock.screen.wallpapers.presentation.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.leansoft.ads.ui.onboarding.LeansoftOnboardingInterface
import com.leansoft.ads.utils.LeansoftAdPlacement
import com.leansoft.ads.utils.LeansoftAdPlacementKt
import com.leansoft.ads.view.NativeAdViewContainer
import com.zip.lock.screen.wallpapers.App
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.databinding.FragmentNativeAdFullscreenBinding
import com.zip.lock.screen.wallpapers.databinding.FragmentPagerIntroBinding
import com.zip.lock.screen.wallpapers.ext.visible
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.set

val leansofttNativeFullPlacements = LeansoftAdPlacementKt.getLeansofttNativeFullPlacements()

@Singleton
class OnboardingImpl @Inject constructor() : LeansoftOnboardingInterface {

    private val bindingMapper: HashMap<LeansoftAdPlacement, ViewBinding> = HashMap()
    private val listenerMapper: HashMap<LeansoftAdPlacement, () -> Unit> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
        placement: LeansoftAdPlacement
    ): View? {
        if (leansofttNativeFullPlacements.contains(placement)) {
            val binding = FragmentNativeAdFullscreenBinding.inflate(inflater, container, false)
            bindingMapper[placement] = binding
            return binding.root
        } else {
            val binding = FragmentPagerIntroBinding.inflate(inflater, container, false)
            bindingMapper[placement] = binding
            return binding.root
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
        placement: LeansoftAdPlacement
    ) {
        if (leansofttNativeFullPlacements.contains(placement)) {
            val binding = bindingMapper[placement] as FragmentNativeAdFullscreenBinding
            binding.btnClose.setOnClickListener {
                listenerMapper[placement]?.invoke()
            }
        } else {
            val binding = bindingMapper[placement] as FragmentPagerIntroBinding
            binding.btnNext.setOnClickListener {
                listenerMapper[placement]?.invoke()
            }
            val context = binding.root.context
            when (placement) {
                LeansoftAdPlacement.NATIVE_OBD_1 -> {
                    binding.dot1.isSelected = true
                    binding.image.setImageResource(R.drawable.img_ob_1)
                    binding.tvTitle.text = context.getString(R.string.intro_title_1)
                    binding.tvContent.text = context.getString(R.string.intro_content_1)
                }

                LeansoftAdPlacement.NATIVE_OBD_2 -> {
                    binding.dot2.isSelected = true
                    binding.image.setImageResource(R.drawable.img_ob_2)
                    binding.tvTitle.text = context.getString(R.string.intro_title_2)
                    binding.tvContent.text = context.getString(R.string.intro_content_2)
                }

                LeansoftAdPlacement.NATIVE_OBD_3 -> {
                    binding.dot3.isSelected = true
                    binding.btnNext.text = context.getString(R.string.msg_started)
                    binding.image.setImageResource(R.drawable.img_ob_3)
                    binding.tvTitle.text = context.getString(R.string.intro_title_3)
                    binding.tvContent.text = context.getString(R.string.intro_content_3)
                }

                else -> {

                }
            }
        }
    }

    override fun getNativeAdContainer(placement: LeansoftAdPlacement): NativeAdViewContainer {
        if (leansofttNativeFullPlacements.contains(placement)) {
            val binding = bindingMapper[placement] as FragmentNativeAdFullscreenBinding
            return binding.nativeDefault
        } else {
            val binding = bindingMapper[placement] as FragmentPagerIntroBinding
            return binding.nativeOnboard
        }
    }

    override fun registerNextClick(
        placement: LeansoftAdPlacement,
        listener: () -> Unit
    ) {
        listenerMapper[placement] = listener
    }

    override fun updateUI(
        placement: LeansoftAdPlacement,
        needEasy: Boolean
    ) {
        if (needEasy) { }
    }
}