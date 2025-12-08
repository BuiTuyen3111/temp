package com.zip.lock.screen.wallpapers.presentation.ui.language

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leansoft.ads.ui.language.LeansoftLanguageInterface
import com.leansoft.ads.view.NativeAdViewContainer
import com.zip.lock.screen.wallpapers.App
import com.zip.lock.screen.wallpapers.R
import com.zip.lock.screen.wallpapers.databinding.FragmentLanguageBinding
import com.zip.lock.screen.wallpapers.domain.model.LanguageModel
import com.zip.lock.screen.wallpapers.ext.visible
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.find
import kotlin.collections.indexOfFirst
import kotlin.let
import kotlin.text.isNotEmpty

@Singleton
class LanguageImpl @Inject constructor() : LeansoftLanguageInterface {

    private lateinit var binding: FragmentLanguageBinding
    private var setLanguageListener: ((String) -> Unit)? = null

    private var selectLanguageEvent: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
        languageCodeSelected: String?
    ) {
        if (languageCodeSelected?.isNotEmpty() == true) {
            binding.btnNext.visible()
            val adapter = LanguageAdapter(onItemClick = { item ->

            }, selectedLanguageCode = languageCodeSelected)
            adapter.setItems(getAllLanguageList())
            binding.recyclerView.adapter = adapter
            binding.btnNext.setOnClickListener {
                setLanguageListener?.invoke(adapter.selectedLanguageCode)
            }
        } else {
            val adapter = LanguageAdapter(onItemClick = { item ->
                selectLanguageEvent?.invoke(item.code)
            }, selectedLanguageCode = "en")
            adapter.setItems(getAllLanguageList())
            binding.recyclerView.adapter = adapter
            binding.btnNext.setOnClickListener {
                setLanguageListener?.invoke("en")
            }
        }
    }

    override fun getNativeAdContainer(): NativeAdViewContainer {
        return binding.nativeDefault
    }

    override fun registerSelectLanguageEvent(listener: (String) -> Unit) {
        selectLanguageEvent = listener
    }

    override fun registerSetLanguageEvent(listener: (String) -> Unit) {
        setLanguageListener = listener
    }


    override fun updateUI(needEasy: Boolean) {}

    companion object {
        fun getAllLanguageList(): MutableList<LanguageModel> {
            val arrayList: MutableList<LanguageModel> = mutableListOf()
            arrayList.add(
                LanguageModel(
                    "English",
                    "en",
                    "English",
                    R.drawable.ic_flag_en
                )
            )
            arrayList.add(
                LanguageModel(
                    "French",
                    "fr",
                    "Français",
                    R.drawable.ic_flag_france
                )
            )
            arrayList.add(
                LanguageModel(
                    "Marathi",
                    "hi",
                    "मराठी (India)",
                    R.drawable.ic_flag_india
                )
            )
            arrayList.add(
                LanguageModel(
                    "Spanish",
                    "es",
                    "Espanol",
                    R.drawable.ic_flag_spain
                )
            )
            arrayList.add(
                LanguageModel(
                    "Chinese",
                    "zh",
                    "Chinese",
                    R.drawable.ic_flag_china
                )
            )
            arrayList.add(
                LanguageModel(
                    "Portuguese",
                    "pt",
                    "Português (Portugal)",
                    R.drawable.ic_flag_portugal
                )
            )
            arrayList.add(
                LanguageModel(
                    "Russian",
                    "ru",
                    "Русский",
                    R.drawable.ic_flag_russia
                )
            )
            arrayList.add(
                LanguageModel(
                    "Indonesian",
                    "in",
                    "Indonesian",
                    R.drawable.ic_flag_indonesia
                )
            )
            arrayList.add(
                LanguageModel(
                    "Filipino",
                    "en-PH",
                    "Philippines",
                    R.drawable.ic_flag_philippines
                )
            )
            arrayList.add(
                LanguageModel(
                    "Bengali",
                    "bn",
                    "বাংলা",
                    R.drawable.ic_flag_bangladesh
                )
            )
            arrayList.add(
                LanguageModel(
                    "Portuguese (Brazil)",
                    "pt-BR",
                    "Português (Brazil)",
                    R.drawable.ic_flag_brazil
                )
            )
            arrayList.add(
                LanguageModel(
                    "Afrikaans",
                    "af-rZA",
                    "Afrikaans",
                    R.drawable.ic_flag_south_africa
                )
            )
            arrayList.add(
                LanguageModel(
                    "German",
                    "de",
                    "Deutsch",
                    R.drawable.ic_flag_germany
                )
            )
            arrayList.add(
                LanguageModel(
                    "English (Canada)",
                    "en-rCA",
                    "Canada",
                    R.drawable.ic_flag_canada
                )
            )
            arrayList.add(
                LanguageModel(
                    "English (UK)",
                    "en-rGB",
                    "English",
                    R.drawable.ic_flag_uk
                )
            )
            arrayList.add(
                LanguageModel(
                    "Korean",
                    "ko",
                    "Korean",
                    R.drawable.ic_flag_south_korea
                )
            )
            arrayList.add(
                LanguageModel(
                    "Dutch",
                    "nl",
                    "Ducth",
                    R.drawable.ic_flag_netherlands
                )
            )
            arrayList.add(
                LanguageModel(
                    "Vietnamese",
                    "vi",
                    "Vietnamese",
                    R.drawable.ic_flag_vietnam
                )
            )

            val defaultLanguage = arrayList.find { it.code == getCurrentLanguage() }
            defaultLanguage?.let {
                arrayList.remove(it)
                arrayList.add(0, it)
            }
            return arrayList
        }

        private fun getCurrentLanguage(): String {
            return Resources.getSystem().configuration.locales[0].language
        }
    }
}