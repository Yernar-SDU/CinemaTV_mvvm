package com.example.cinematv_mvvm.ui.profile

import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.cloud.repository.BaseCloudRepository
import com.example.cinematv_mvvm.data.prefs.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    val baseCloudRepository: BaseCloudRepository,
    val prefs: Prefs): BaseViewModel() {

        fun isUserLoggedIn(): Boolean{
            return prefs.isLoggedIn()
        }


}