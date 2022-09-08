package me.auth.com.authentication.service.auth.impl

import me.auth.com.authentication.api.exception.handler.ErrorCode
import me.auth.com.authentication.command.StatusEnum
import me.auth.com.authentication.command.helper.err
import me.auth.com.authentication.command.helper.getOrElseThrow
import me.auth.com.authentication.command.helper.toLocalDateTime
import me.auth.com.authentication.command.helper.toSHA256Base64
import me.auth.com.authentication.domain.model.AccessToken
import me.auth.com.authentication.domain.model.Auth
import me.auth.com.authentication.domain.projection.AccessTokenProjection
import me.auth.com.authentication.repository.AccessTokenRepository
import me.auth.com.authentication.repository.StatusRepository
import me.auth.com.authentication.security.JwtProvider
import me.auth.com.authentication.security.SecurityProperties
import me.auth.com.authentication.service.auth.IAccessTokenService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

//@Service
class AccessTokenService(
    private val accessTokenRepo: AccessTokenRepository,
    private val jwtProvider: JwtProvider,
    private val statusRepo: StatusRepository,
    private val prop: SecurityProperties,
) : IAccessTokenService {

    @Transactional
    override fun updateAccessToken(
        user: Auth,
        token: String,
        refreshTokenData: RefreshTokenData,
        userAgent: String,
        clientIP: String
    ): List<AccessToken> {
        val tokenExpirationDateTime = jwtProvider.getExpirationDate(token).toLocalDateTime()
        val hashToken = token.toSHA256Base64()
        val accessTokens = accessTokenRepo.findByUserId(user.id)
        val activeStatus = getOrElseThrow("Status", StatusEnum.ACTIVE.id, statusRepo::findById)
        return if (accessTokens.isEmpty()) {
            accessTokenRepo.saveAll(
                listOf(
                    AccessToken(
                        hashToken = hashToken,
                        refreshToken = refreshTokenData.refreshToken,
                        refreshTokenExpiredDateTime = refreshTokenData.expiredAt,
                        userAgent = userAgent,
                        clientIp = clientIP,
                        lastDateTimeRequest = LocalDateTime.now(),
                        expiredDateTime = tokenExpirationDateTime,
                        user = user,
                        status = activeStatus
                    ).also {
                        it.refreshTokenUsedTimes =
                            if (refreshTokenData.isRefreshing) (it.refreshTokenUsedTimes ?: 0) + 1
                            else 0
                    }
                )
            )
        } else {
            accessTokens.first().hashToken = hashToken
            accessTokens.first().refreshToken = refreshTokenData.refreshToken
            accessTokens.first().refreshTokenExpiredDateTime = refreshTokenData.expiredAt
            accessTokens.first().userAgent = userAgent
            accessTokens.first().clientIp = clientIP
            accessTokens.first().expiredDateTime = tokenExpirationDateTime
            accessTokens.first().lastDateTimeRequest = LocalDateTime.now()
            accessTokens.first().refreshTokenUsedTimes =
                if (refreshTokenData.isRefreshing) (accessTokens.first().refreshTokenUsedTimes ?: 0) + 1 else 0
            accessTokenRepo.saveAll(accessTokens)
        }
    }

    @Transactional
    override fun reset(token: String): AccessToken? {
        val accessToken = accessTokenRepo.findByHashToken(token.toSHA256Base64(), AccessToken::class.java)
        return accessToken?.let { accessTokenRepo.save(it.reset()!!) }
    }

    @Transactional
    override fun internalReset(userId: Long) {
        accessTokenRepo.findByUserId(userId).map {
            it.reset()!!
        }.also { accessTokenRepo.saveAll(it) }
    }

    fun getUserByRefreshToken(refreshToken: String): Auth {
        val userEntity= accessTokenRepo.findByRefreshToken(refreshToken) ?: err(ErrorCode.ID_NOT_FOUND)
        val accessToken: AccessTokenProjection.UserEntity = userEntity as AccessTokenProjection.UserEntity

        if ((accessToken.refreshTokenUsedTimes ?: 0) >= prop.jwt.refreshTokenMaxUsedTime) err(ErrorCode.MAX_USED_REFRESH_TOKEN)
        return accessToken.user
    }

    private fun AccessToken?.reset(): AccessToken? {
        this?.let {
            it.hashToken = null
            it.refreshTokenExpiredDateTime = null
            it.refreshToken = null
            it.refreshTokenUsedTimes = 0
            return it
        }
        return null
    }
}

data class RefreshTokenData(val refreshToken: String, val expiredAt: LocalDateTime, val isRefreshing: Boolean = false)