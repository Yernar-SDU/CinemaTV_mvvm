package com.example.cinematv_mvvm.ui.home

import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.prefs.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefs: Prefs) : BaseViewModel() {


        fun checkForFirstTimeEnter(){
            prefs.isUserFirstTime()
        }
}