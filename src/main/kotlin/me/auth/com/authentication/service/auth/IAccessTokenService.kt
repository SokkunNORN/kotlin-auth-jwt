package me.auth.com.authentication.service.auth

import me.auth.com.authentication.domain.model.AccessToken
import me.auth.com.authentication.domain.model.Auth
import me.auth.com.authentication.service.auth.impl.RefreshTokenData

interface IAccessTokenService {
    fun updateAccessToken(user: Auth, token: String, refreshTokenData: RefreshTokenData, userAgent: String, clientIP: String): List<AccessToken>
    fun reset(token: String): AccessToken?
    fun internalReset(userId: Long)
}