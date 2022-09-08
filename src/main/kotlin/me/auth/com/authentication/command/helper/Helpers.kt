package me.auth.com.authentication.command.helper

import com.fasterxml.jackson.databind.ObjectMapper
import me.auth.com.authentication.api.exception.ClientErrorException
import me.auth.com.authentication.api.exception.handler.*
import me.auth.com.authentication.api.response.helper.PageResponse
import me.auth.com.authentication.api.response.helper.Pagination
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mapping.PropertyPath
import org.springframework.data.mapping.PropertyReferenceException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

infix fun Boolean.then(action: () -> Unit): Boolean {
    if (this)
        action.invoke()
    return this
}

fun <T> getOrElseThrow(name: String, id: Long, loader: (Long) -> Optional<T>): T {
    return loader(id).orElseThrow { throw IdNotFoundException(name, id) }
}

fun <T> getOrElseThrow(name: String, field: String, loader: (String) -> Optional<T>): T {
    return loader(field).orElseThrow { throw ParamNotFoundException("$name[$field]") }
}

fun <T> getOrElseThrow(name: String, field: T?): T {
    return field ?: throw ParamNotFoundException("The param[$name]")
}

fun <T> Optional<T>.orElseThrow(msg: String): T {
    return this.orElseThrow { throw ParamNotFoundException(msg) }
}

fun Boolean.existsAndThrow(msg: String): Boolean {
    this.then { throw RecordExistException(msg) }
    return this
}

fun <T> Pageable.checkingSortFields(type: Class<T>) {
    this.sort.toList().map {
        try {
            PropertyPath.from(it.property, type)
        } catch (e: PropertyReferenceException) {
            throw ClientErrorException(ErrorCode.UNRECOGNIZED_FIELD, e.propertyName)
        }
    }
}

fun <T> Page<T>.toPageResponse(): PageResponse<T> {
    return PageResponse(
        content = this.content,
        pagination = Pagination(
            currentPage = this.pageable.pageNumber,
            pageSize = this.pageable.pageSize,
            totalElements = this.totalElements,
            totalPages = this.totalPages
        )
    )
}

fun <T> T.toMap(): Map<*, *> {
    return ObjectMapper().convertValue(this, Map::class.java)
}

fun err(error: ErrorCode, mes: String? = null) {
    throw ClientErrorException(error, mes)
}

fun Date.toLocalDateTime(): LocalDateTime {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}

fun String.toSHA256Base64(): String {
    val byteHash = MessageDigest.getInstance("SHA-256").digest(this.toByteArray(StandardCharsets.UTF_8))
    return Base64.getEncoder().encodeToString(byteHash)
}