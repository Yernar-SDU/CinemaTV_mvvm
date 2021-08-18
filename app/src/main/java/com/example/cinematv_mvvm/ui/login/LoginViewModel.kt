package com.example.cinematv_mvvm.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import com.example.cinematv_mvvm.core.BaseViewModel
import com.example.cinematv_mvvm.data.cloud.ResultWrapper
import com.example.cinematv_mvvm.data.cloud.repository.BaseCloudRepository
import com.example.cinematv_mvvm.data.prefs.Prefs
import com.example.cinematv_mvvm.model.LoginValidation
import com.example.cinematv_mvvm.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


@HiltViewModel
class LoginViewModel @Inject constructor(private val baseCloudRepository: BaseCloudRepository
    ,val context: Context,val prefs: Prefs): BaseViewModel() {

    val requestToken = MutableLiveData(String())
    val session_id = MutableLiveData(String())
    val success = MutableLiveData(false)

    fun isAuthorized(): Boolean {
        return prefs.isAuthorized()
    }

    fun setAuthorized(authorized: Boolean){
        return prefs.setAuthorized(authorized)
    }

    fun getSessionId(): String {
        return prefs.getSessionId()
    }

    fun setSessionId(sessionId: String){
        prefs.setSessionId(sessionId)
    }

    fun getRequestToken() {
        launchIO {
            when (val wrapper = baseCloudRepository.getRequestToken()) {
                is ResultWrapper.Success -> {
                    Log.i("upsTAKE", "getRequestToken: ${wrapper.value}")
                    requestToken.postValue(wrapper.value.request_token)
                }
                is ResultWrapper.Error -> {

                }
            }

        }
    }


    fun loginValidate(username: String, password: String, request_token: String){
        launchIO {
            val loginValidation = LoginValidation(username, password, requestToken.value.toString())
            when (val wrapper = baseCloudRepository.login(loginValidation)) {
                is ResultWrapper.Success -> {
                    success.postValue(wrapper.value.success)
                    getSessionIdRequest()
                }
                is ResultWrapper.Error -> {
                    success.postValue(false)
                }
            }

        }
    }

    fun getSessionIdRequest(){
        launchIO {
            val loginValidation = LoginValidation("lust4alife","ernar2020766872",requestToken.value.toString())
            when(val wrapper = baseCloudRepository.getSessionId(loginValidation)){
                is ResultWrapper.Success ->{
                    session_id.postValue(wrapper.value.session_id)
                    prefs.setSessionId(wrapper.value.session_id)
                    setAuthorized(true)
                    Log.i("upsTAKE", "getSessionId: ${wrapper.value}")
                }
                is ResultWrapper.Error -> {
                }
            }

        }
    }







}