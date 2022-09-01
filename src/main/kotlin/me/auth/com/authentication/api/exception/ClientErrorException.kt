package me.auth.com.authentication.api.exception

import me.auth.com.authentication.api.exception.handler.ErrorCode

data class ClientErrorException(val errorCode: ErrorCode, val description: String?) : RuntimeException(description)
