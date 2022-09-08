package me.auth.com.authentication.service.auth.helper

import me.auth.com.authentication.api.exception.handler.ErrorCode
import me.auth.com.authentication.api.request.auth.AuthReq
import me.auth.com.authentication.api.response.auth.AuthRes
import me.auth.com.authentication.command.helper.err
import me.auth.com.authentication.domain.model.AccessToken
import me.auth.com.authentication.domain.model.Auth
import me.auth.com.authentication.repository.PasswordPolicyRepository
import me.auth.com.authentication.repository.StatusRepository
import me.auth.com.authentication.repository.UserRepository
import me.auth.com.authentication.security.JwtProvider
import me.auth.com.authentication.security.SecurityProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.LockModeType
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.JoinType
import javax.transaction.Transactional

@Service
class AuthValidator(
    private val jwtProvider: JwtProvider,
    private val em: EntityManager,
) {
    @Transactional
    fun login(request: AuthReq): AuthRes {
        val user: Auth = em.deepFetchAuthByUsername(request.username!!)
        lateinit var authRes: AuthRes

        kotlin.runCatching {
            this.validateAndGetToken(request, user)
        }.onSuccess {
            authRes = it
        }.onFailure {
            throw it
        }

        return authRes
    }

    fun validatePassword(request: AuthReq, user: Auth) {
        if (!BCryptPasswordEncoder().matches(request.password, user.password)) {
            err(ErrorCode.INCORRECT_LOGIN)
        }
    }

    fun generateToken(user: Auth): AuthRes {
        val authenticationToken = UsernamePasswordAuthenticationToken(user.username, user.password)
        val token = jwtProvider.createToken(authenticationToken, user.toUserAuth())

        return user.toResponse().apply {
            this.token = token
        }
    }

    fun validateAndGetToken(request: AuthReq, user: Auth): AuthRes {
        this.validatePassword(request, user)
        return generateToken(user)
    }

    fun EntityManager.deepFetchAuthByUsername(username: String): Auth {
        val cb = this.criteriaBuilder
        val query: CriteriaQuery<Auth> = cb.createQuery(Auth::class.java)
        val root = query.from(Auth::class.java)
        root.fetch<Auth, AccessToken>(Auth::accessToken.name, JoinType.LEFT)
        query.select(root)
            .where(cb.equal(root.get<String>(Auth::username.name), username))
        val users: List<Auth> = this.createQuery(query).resultList
        if (users.isEmpty()) err(ErrorCode.INCORRECT_LOGIN)
        em.lock(users[0], LockModeType.PESSIMISTIC_WRITE)
        return users[0]
    }
}