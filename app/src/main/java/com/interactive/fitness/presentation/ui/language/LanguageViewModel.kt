package com.interactive.fitness.presentation.ui.language

import android.content.Context
import com.interactive.fitness.domain.model.LanguageModel
import com.interactive.fitness.presentation.ui.base.BaseVM
import com.interactive.fitness.utils.AppLanguageUtils
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
        val languageList = LanguageImpl.Companion.getAllLanguageList()
        val currentLanguage = AppLanguageUtils.getLanguageModel(context)
        return languageList.indexOfFirst { it.code == currentLanguage?.code }
    }

    fun getSelectedLanguage(): LanguageModel? {
        val languageList = LanguageImpl.Companion.getAllLanguageList()
        val currentLanguage = AppLanguageUtils.getLanguageModel(context)
        return languageList.firstOrNull { it.code == currentLanguage?.code }
    }
}