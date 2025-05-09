package com.template

import android.app.Activity

sealed class AuthIntent {
    object CheckUser : AuthIntent()
    data class AuthUsers(val email: String, val password: String, val activity: Activity):AuthIntent()
}