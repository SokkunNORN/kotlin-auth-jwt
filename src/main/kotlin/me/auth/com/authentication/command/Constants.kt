package me.auth.com.authentication.command

import org.springframework.security.core.Authentication
import java.math.BigDecimal
import javax.naming.Context

object Constants {
    const val UNKNOWN = "unknown"
    const val E_TOKEN_LOGIN_TIME_LENGTH_SECOND = 300L
    const val RETRY_5: Long = 5
    const val RETRY_10: Long = 10
    const val TIMEOUT_10_SECONDS: Long = 10_000
    const val TIMEOUT_20_SECONDS: Long = 20_000
    const val TIMEOUT_60_SECONDS: Long = 60_000
    const val TIMEOUT_120_SECONDS: Long = 120_000
    const val DELAY_RETRY_2_SECONDS: Long = 2_000
    const val EXPIRATION_KEY_LIFE_MINUTE = 10L

    const val ALLOWED_PASSWORD_RE_USE_TIMES = 13
    const val ALLOWED_LAST_PASSWORD_CHANGED_HOUR = 24
    const val ALLOWED_CHANGING_PASSWORD_TIMES_PER_DAY = 9999
    const val BEARER = "Bearer "

    const val LOGIN_ATTEMPT_LIMIT = 5L// Check 60 times 60 * 30 = 1800s (half hours), Testing only 6 times 6 * 30 = 180s (3 minutes)

    val MIN_KHR_AMOUNT: BigDecimal = BigDecimal.valueOf(1)
    val MAX_KHR_AMOUNT: BigDecimal = BigDecimal.valueOf(9_999_999_999)
    val MIN_USD_AMOUNT: BigDecimal = BigDecimal.valueOf(0.01)
    val MAX_USD_AMOUNT: BigDecimal = BigDecimal.valueOf(999_999)
}