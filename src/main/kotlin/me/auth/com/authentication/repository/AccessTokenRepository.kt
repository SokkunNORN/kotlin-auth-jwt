package me.auth.com.authentication.repository

import me.auth.com.authentication.domain.model.AccessToken
import org.springframework.data.jpa.repository.JpaRepository

interface AccessTokenRepository: JpaRepository<AccessToken, Long>