package me.auth.com.authentication.api.response.auth

data class AuthRes(
    val id: Long,
    val username: String,
    var token: String?
)