package me.auth.com.authentication.service.auth

import me.auth.com.authentication.api.request.auth.AuthReq
import me.auth.com.authentication.api.response.auth.AuthRes

interface IAuthService {
    fun login(request: AuthReq): AuthRes
}