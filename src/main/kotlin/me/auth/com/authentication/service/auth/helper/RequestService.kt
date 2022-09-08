package me.auth.com.authentication.service.auth.helper

import liquibase.repackaged.org.apache.commons.lang3.ObjectUtils
import org.springframework.stereotype.Service
import ua_parser.Client
import ua_parser.Parser
import java.net.InetAddress
import java.net.UnknownHostException
import javax.servlet.http.HttpServletRequest

@Service
class RequestService(
    private val request: HttpServletRequest
) {
    companion object {
        private const val LOCALHOST_IPV4 = "127.0.0.1"
        private const val LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1"
    }

    fun getClientIp(): String? {
        var ipAddress = request.getHeader("X-Forwarded-For")
        if (ObjectUtils.isEmpty(ipAddress) || "unknown".equals(ipAddress, ignoreCase = true)) {
            ipAddress = request.getHeader("Proxy-Client-IP")
        }
        if (ObjectUtils.isEmpty(ipAddress) || "unknown".equals(ipAddress, ignoreCase = true)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ObjectUtils.isEmpty(ipAddress) || "unknown".equals(ipAddress, ignoreCase = true)) {
            ipAddress = request.remoteAddr
            if (LOCALHOST_IPV4 == ipAddress || LOCALHOST_IPV6 == ipAddress) {
                try {
                    val inetAddress = InetAddress.getLocalHost()
                    ipAddress = inetAddress.hostAddress
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                }
            }
        }
        if (!ObjectUtils.isEmpty(ipAddress) &&
            ipAddress.length > 15 && ipAddress.indexOf(",") > 0
        ) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","))
        }
        return ipAddress
    }

    fun getUserAgent(): String {
        return request.getHeader("user-agent")
    }

    fun parseUserAgent(userAgent: String?): Client {
        return Parser().parse(userAgent)
    }
}