package me.auth.com.authentication.config

import me.auth.com.authentication.api.exception.ClientErrorException
import me.auth.com.authentication.api.exception.handler.ErrorCode
import me.auth.com.authentication.command.helper.err
import me.auth.com.authentication.security.JwtProvider
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
): OncePerRequestFilter() {

    private val AUTHORIZATION_HEADER = "Authorization"

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val log = LoggerFactory.getLogger(this.javaClass)

        log.info("The fun is called...")
        val token = getToken(request)
        try {
            log.info("The fun is called...${StringUtils.hasText(token)}")
            if (StringUtils.hasText(token)) {
                val userAuth = jwtProvider.getUserAuth(token)
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(userAuth, token)
            }
        } catch (e: ClientErrorException) {

            log.info("Error: catch")
            err(ErrorCode.INVALID_TOKEN)
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken (request: HttpServletRequest): String {
        val log = LoggerFactory.getLogger(this.javaClass)
        val requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER)

        log.info("Get token")

        requestTokenHeader ?: err(ErrorCode.UNAUTHORIZED_ACCESS)
        if (!requestTokenHeader.startsWith("Bearer ")) err(ErrorCode.INVALID_TOKEN)

        return requestTokenHeader.substring(7)
    }
}