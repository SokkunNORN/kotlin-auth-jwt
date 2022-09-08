package me.auth.com.authentication.security

data class UserAuth(
    val roleId: Long,
    val userId: Long,
    val roleType: String
)
