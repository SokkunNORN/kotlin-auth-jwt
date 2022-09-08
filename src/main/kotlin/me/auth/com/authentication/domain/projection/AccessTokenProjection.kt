package me.auth.com.authentication.domain.projection

import me.auth.com.authentication.domain.model.Auth
import java.time.LocalDateTime

interface AccessTokenProjection {
    interface User {
        val userRegistrationDateTime: LocalDateTime?
        val userRegistrationKey: String?
    }

    interface UserEntity {
        val user: Auth
        val refreshTokenUsedTimes: Int?
    }
}