package me.auth.com.authentication.command.helper

import me.auth.com.authentication.api.exception.ClientErrorException
import me.auth.com.authentication.api.exception.handler.ErrorCode
import me.auth.com.authentication.command.GenderEnum
import java.util.regex.Pattern

fun String?.validGender() : String? {
    return this?.let { gender ->
        val genderList = GenderEnum.values().map { it.name.lowercase() }.toSet()
        if (gender.lowercase() in genderList) {
            return gender
        }
        throw ClientErrorException(ErrorCode.INVALID_INPUT_FORMAT, "gender")
    }
}

fun String?.validEmail() : String? {
    return this?.let {
        val pattern = Pattern.compile(
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        )

        if (pattern.matcher(it).matches()) {
            return it
        }
        throw ClientErrorException(ErrorCode.INVALID_INPUT_FORMAT, "email")
    }
}

fun String?.validPhone() : String? {
    return this?.let {
        val pattern = Pattern.matches("(855)[0-9]{8,9}", it)

        if (pattern) return it
        throw ClientErrorException(ErrorCode.INVALID_INPUT_FORMAT, "phoneNumber")
    }
}