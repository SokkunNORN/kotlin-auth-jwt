package me.auth.com.authentication.service.auth.impl

import me.auth.com.authentication.api.request.auth.AuthReq
import me.auth.com.authentication.api.response.auth.AuthRes
import me.auth.com.authentication.repository.UserRepository
import me.auth.com.authentication.security.JwtProvider
import me.auth.com.authentication.service.auth.IAuthService
import me.auth.com.authentication.service.auth.helper.AuthValidator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepo: UserRepository,
    private val userService: UserService,
    private val jwtProvider: JwtProvider,
    private val authenticationManager: AuthenticationManager,
    private val authValidator: AuthValidator
) : IAuthService {
    override fun login(request: AuthReq): AuthRes {
        val login = authValidator.login(request)

        return login
    }
}