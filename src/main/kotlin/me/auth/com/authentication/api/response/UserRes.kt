package me.auth.com.authentication.api.response

import me.auth.com.authentication.domain.model.Status

data class UserRes(
    val id: Long,
    val name: String,
    val username: String,
    val gender: String?,
    val email: String?,
    val phoneNumber: String?,
    val address: String?,
    val status: Status,
    val createdAt: String?,
    val updatedAt: String?
)
