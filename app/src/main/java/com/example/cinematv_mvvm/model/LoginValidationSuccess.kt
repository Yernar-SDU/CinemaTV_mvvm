package com.example.cinematv_mvvm.model

data class LoginValidationSuccess(
    val success: Boolean,
    val session_id: String,
    val expires_at: String,
    val request_token: String
) {

}