package com.zip.lock.screen.wallpapers.ads

import androidx.databinding.BindingAdapter
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.leansoft.ads.view.BannerAdViewContainer
import com.leansoft.ads.view.NativeAdViewContainer

@BindingAdapter("native_ads_placement")
fun setAdPlacementString(view: NativeAdViewContainer, placementValue: String?) {
    view.adsPlacement = placementValue ?: ""
}

@BindingAdapter("native_ads_reload_collapsible")
fun setAdReloadCollapsible(view: NativeAdViewContainer, time: Long) {
    view.reloadInterval = time
}

@BindingAdapter("banner_ads_placement")
fun setBannerAdPlacementString(view: BannerAdViewContainer, placementValue: String?) {
    view.placement = placementValue ?: ""
}

@BindingAdapter("bindReloadNativeCollab")
fun NativeAdViewContainer.bindReloadNativeCollab(value: Long? = null) {
    val interval = FirebaseRemoteConfig.getInstance().getLong("reload_native_collapsible") * 1_000L
    this.reloadInterval = value ?: interval
}