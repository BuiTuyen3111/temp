package com.zip.lock.screen.wallpapers.ads

import com.zip.lock.screen.wallpapers.BuildConfig

enum class AdPlacement(var key: String, var id: String) {
    NATIVE_EXIT(
        key = "native_exit",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110" // test native
        else
            "ca-app-pub-5889155949011891/2786718858"
    ),

    INTER_RESUME(
        key = "inter_resume",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/1033173712" // test interstitial
        else
            "ca-app-pub-5889155949011891/6515895082"
    ),

    NATIVE_FULL_INTER(
        key = "native_full_inter",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/3601107070"
    ),

    NATIVE_FULL_REWARD(
        key = "native_full_reward",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/3601107070"
    ),

    INTER_BACK(
        key = "inter_back",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/1033173712"
        else
            "ca-app-pub-5889155949011891/6515895082"
    ),

    INTER_HOME(
        key = "inter_home",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/1033173712"
        else
            "ca-app-pub-5889155949011891/6515895082"
    ),

    INTER_LISTING(
        key = "inter_listing",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/1033173712"
        else
            "ca-app-pub-5889155949011891/6515895082"
    ),

    NATIVE_CUSTOMIZE(
        key = "native_customize",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/2786718858"
    ),

    INTER_SUCCESSFUL(
        key = "inter_sucessful",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/1033173712"
        else
            "ca-app-pub-5889155949011891/6515895082"
    ),

    NATIVE_SUCCESSFUL(
        key = "native_successful",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/2786718858"
    ),

    REWARD_IN_APP(
        key = "reward_in_app",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/5224354917"
        else
            "ca-app-pub-5889155949011891/3281041862"
    ),

    //////////////////////////////////

    INTER_SPLASH(
        key = "inter_splash",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/1033173712"
        else
            "ca-app-pub-5889155949011891/2923967538"
    ),

    NATIVE_FULL_SPLASH(
        key = "native_full_splash",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110" // test native
        else
            "ca-app-pub-5889155949011891/2786718858"
    ),

    INTER_SPLASH_BACKUP(
        key = "inter_splash_backup",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/1033173712"
        else
            "ca-app-pub-5889155949011891/9600906198"
    ),

    NATIVE_LANGUAGE(
        key = "native_language",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/8081625175"
    ),

    NATIVE_CLICK(
        key = "native_click",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/9026191124"
    ),

    NATIVE_OBD_1(
        key = "native_obd_1",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/9728930526"
    ),

    NATIVE_FULL_OBD_1(
        key = "native_obd_full_1",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/5585956791"
    ),

    NATIVE_OBD_2(
        key = "native_obd_2",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/5585956791"
    ),

    NATIVE_FULL_OBD_2(
        key = "native_obd_full_2",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/5874280147"
    ),

    NATIVE_OBD_3(
        key = "native_obd_3",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/5874280147"
    ),

    NATIVE_UNINSTALL(
        key = "native_uninstall",
        id = if (BuildConfig.DEBUG)
            "ca-app-pub-3940256099942544/2247696110"
        else
            "ca-app-pub-5889155949011891/2786718858"
    ),

    NATIVE_COLLAP_HOME(
        key = "native_collap_home",
        id = "ca-app-pub-3940256099942544/2247696110"
    ),

    NATIVE_COLLAP_LISTING(
        key = "native_collap_listing",
        id = "ca-app-pub-3940256099942544/2247696110"
    ),

    NATIVE_COLLAP_CROP(
        key = "native_collap_crop",
        id = "ca-app-pub-3940256099942544/2247696110"
    ),

    NATIVE_COLLAP_CUSTOMIZE(
        key = "native_collap_customize",
        id = "ca-app-pub-3940256099942544/2247696110"
    ),

    NATIVE_COLLAP_DETAIL(
        key = "native_collap_detail",
        id = "ca-app-pub-3940256099942544/2247696110"
    ),

    NATIVE_OVERLAY_SUCCESSFUL(
        key = "native_overlay_successful",
        id = "ca-app-pub-3940256099942544/2247696110"
    );
}