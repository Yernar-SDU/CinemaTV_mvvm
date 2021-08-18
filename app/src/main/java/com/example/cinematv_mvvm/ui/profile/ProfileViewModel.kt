package com.example.cinematv_mvvm.ui.profile

import android.util.Log
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
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    val baseCloudRepository: BaseCloudRepository,
    val prefs: Prefs
) : BaseViewModel() {
    val requestToken = MutableLiveData(String())
    val session_id = MutableLiveData(String())
    val success = MutableLiveData(false)
    val user = MutableLiveData(User())

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

    fun userEnteredSuccess() {
        setAuthorized(true)
        getSessionId(requestToken.value.toString())
    }


    fun getRequestToken() {
        launchIO {
            val a = async {
                when (val wrapper = baseCloudRepository.getRequestToken()) {
                    is ResultWrapper.Success -> {
                        Log.i("upsTAKE", "getRequestToken: ${wrapper.value}")
                        requestToken.postValue(wrapper.value.request_token)
                    }
                    is ResultWrapper.Error -> {

                    }
                }
            }
            a.join()
        }
    }

    fun getSessionId(request_token: String){
        launchIO {
            Log.i("upsTAKE", "getSessionId: ${request_token}")
            val loginValidation = LoginValidation("lust4alife","ernar2020766872",request_token)
            when(val wrapper = baseCloudRepository.getSessionId(loginValidation)){
                is ResultWrapper.Success ->{
                    session_id.postValue(wrapper.value.session_id)
                    prefs.setSessionId(wrapper.value.session_id)
                    Log.i("upsTAKE", "getSessionId: ${wrapper.value}")
                }
                is ResultWrapper.Error -> {
                    Log.i("upsTAKE", "getSessionId: ${wrapper.error}")
                }
            }
        }
    }

    fun initializeUserInfo(){
        launchIO {
            if (getSessionId().isNotEmpty()){
                val wrapper = baseCloudRepository.getAccountDetails(getSessionId())
                Log.i("upsTAKE", "initializeUserInfo: ${getSessionId().toString()}")

                when(wrapper){
                    is ResultWrapper.Success -> {
                        user.postValue(wrapper.value)
                        Log.i("upsTAKE", "initializeUserInfo: ${wrapper.value}")
                    }
                    is ResultWrapper.Error -> {
                        Log.i("upsTAKE", "initializeUserInfo: ${wrapper.status}")
                    }
                }
            }
        }
    }

}