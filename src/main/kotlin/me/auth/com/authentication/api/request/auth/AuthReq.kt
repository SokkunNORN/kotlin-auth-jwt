package me.auth.com.authentication.api.request.auth

import me.auth.com.authentication.command.helper.getOrElseThrow
data class AuthReq(
    val username: String? = null,
    val password: String? = null
) {
    init {
        getOrElseThrow("username", username)
        getOrElseThrow("password", password)
    }
}
