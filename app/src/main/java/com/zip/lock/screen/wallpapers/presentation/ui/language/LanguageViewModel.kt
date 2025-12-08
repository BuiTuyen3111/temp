package com.zip.lock.screen.wallpapers.presentation.ui.language

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.zip.lock.screen.wallpapers.domain.model.LanguageModel
import com.zip.lock.screen.wallpapers.presentation.ui.base.BaseVM
import com.zip.lock.screen.wallpapers.presentation.ui.language.LanguageImpl.Companion.getAllLanguageList
import com.zip.lock.screen.wallpapers.utils.AppLanguageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseVM() {

    companion object {
        const val TAG = "LanguageViewModel"
    }

    private var mLanguageModel: LanguageModel? = null

    fun setLanguageModel(languageModel: LanguageModel) {
        mLanguageModel = languageModel
    }

    fun getLanguageModel(): LanguageModel? {
        return mLanguageModel
    }

    fun getSelectedLanguagePosition(): Int {
        val languageList = getAllLanguageList()
        val currentLanguage = AppLanguageUtils.getLanguageModel(context)
        return languageList.indexOfFirst { it.code == currentLanguage?.code }
    }

    fun getSelectedLanguage(): LanguageModel? {
        val languageList = getAllLanguageList()
        val currentLanguage = AppLanguageUtils.getLanguageModel(context)
        return languageList.firstOrNull { it.code == currentLanguage?.code }
    }
}