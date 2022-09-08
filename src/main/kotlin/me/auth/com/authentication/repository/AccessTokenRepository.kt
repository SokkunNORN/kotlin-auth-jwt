package me.auth.com.authentication.repository

import me.auth.com.authentication.domain.model.AccessToken
import me.auth.com.authentication.domain.projection.AccessTokenProjection
import org.springframework.data.jpa.repository.JpaRepository

interface AccessTokenRepository: JpaRepository<AccessToken, Long> {
    fun findByUserId(userId: Long): List<AccessToken>
    fun <T> findByHashToken(hashToken: String, type: Class<T>): T?
    fun findByRefreshToken(refreshToken: String): AccessTokenProjection.UserEntity?
}