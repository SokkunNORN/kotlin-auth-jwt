package me.auth.com.authentication.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import me.auth.com.authentication.api.exception.handler.ErrorCode
import me.auth.com.authentication.command.helper.toMap
import me.auth.com.authentication.repository.AccessTokenRepository
import me.auth.com.authentication.service.auth.IAuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.security.GeneralSecurityException
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.SecretKey
import kotlin.jvm.Throws

@Component
open class JwtProvider(
    private val securityProperties: SecurityProperties
) {
    private val LOG: Logger = LoggerFactory.getLogger(this.javaClass)
    private lateinit var key: SecretKey

    companion object {
        private const val USER_CLAIM = "user"
        private const val AUTHORITIES_CLAIM = "authorities"
    }

    @PostConstruct
    fun init() {
        LOG.info("The init is called...")
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityProperties.jwt.secretBase64))
    }

    @Throws(GeneralSecurityException::class)
    fun createToken(authentication: Authentication, userAuth: UserAuth): String {
        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_CLAIM, authentication.authorities.map { it.authority })
            .claim(USER_CLAIM, userAuth.toMap())
            .setExpiration(securityProperties.jwt.tokenExpiredLifeTime.expireTimeFromNow())
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun getClaimsJws(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val claimsJws = this.getClaimsJws(token)
        val userAuth = ObjectMapper().convertValue(claimsJws[USER_CLAIM], UserAuth::class.java)
        val authorities = ObjectMapper().convertValue(claimsJws[AUTHORITIES_CLAIM], List::class.java)

        return UsernamePasswordAuthenticationToken(
            userAuth,
            token,
            authorities.map { GrantedAuthority { it.toString() }}
        )
    }

    fun getUserAuth(token: String): UserAuth {
        return this.getAuthentication(token).principal as UserAuth
    }

    fun getAuthorities(token: String): Collection<GrantedAuthority> {
        return ObjectMapper().convertValue(this.getClaimsJws(token)[AUTHORITIES_CLAIM], List::class.java)
            .map { GrantedAuthority { it.toString() } }
    }

    @Throws(GeneralSecurityException::class)
    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken)
            return true
        } catch (e: io.jsonwebtoken.security.SecurityException) {
            LOG.info("Invalid JWT signature.")
            LOG.trace("Invalid JWT signature trace", e)
        } catch (e: MalformedJwtException) {
            LOG.info("Invalid JWT token.")
            LOG.trace("Invalid JWT token trace", e)
        } catch (e: ExpiredJwtException) {
            LOG.info("Expired JWT token.")
            LOG.trace("Expired JWT token trace", e)
            error(ErrorCode.TOKEN_EXPIRED)
        } catch (e: UnsupportedJwtException) {
            LOG.info("Unsupported JWT token.")
            LOG.trace("Unsupported JWT token trace", e)
        } catch (e: IllegalArgumentException) {
            LOG.info("JWT token compact of handler are invalid.")
            LOG.trace("JWT token compact of handler are invalid trace", e)
        }
        error(ErrorCode.INVALID_TOKEN)
    }

    fun getExpirationDate(token: String): Date {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.expiration
    }

    fun Long.expireTimeFromNow(): Date {
        return Date(System.currentTimeMillis() + this * 1000)
    }
}