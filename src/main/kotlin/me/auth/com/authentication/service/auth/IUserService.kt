package me.auth.com.authentication.service.auth

import me.auth.com.authentication.api.request.UserReq
import me.auth.com.authentication.api.response.UserRes

interface IUserService {
    fun create(request: UserReq): UserRes
    fun getAll(): List<UserRes>
}