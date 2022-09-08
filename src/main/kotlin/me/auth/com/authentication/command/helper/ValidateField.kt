package me.auth.com.authentication.command.helper

import me.auth.com.authentication.api.exception.ClientErrorException
import me.auth.com.authentication.api.exception.handler.ErrorCode
import me.auth.com.authentication.command.GenderEnum
import me.auth.com.authentication.domain.model.PasswordPolicy
import java.util.regex.Pattern
import kotlin.jvm.internal.Intrinsics

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
        val pattern = Pattern.compile("855[\\d]{8,9}")
        if (pattern.matcher(this).matches()) return it
        throw ClientErrorException(ErrorCode.INVALID_INPUT_FORMAT, "phoneNumber")
    }
}

fun String.matchPasswordPolicy(passwordPolicy: PasswordPolicy): Boolean {
    val passwordPolicyRegex = Pattern.compile(passwordPolicy.passwordPolicyRegex())
    return passwordPolicyRegex.matcher(this).matches()
}

fun PasswordPolicy.passwordPolicyRegex() : String {
    return "((?=(.*\\d){${this.minNumNumeric}})" +
            "(?=(.*[a-z]){${this.minNumLowerLetter}})" +
            "(?=(.*[A-Z]){${this.minNumUpperLetter}})" +
            "(?=(.*[@#$%^&*\\-_!+=\\[\\]{}|:,.?/`~\"()]){${this.minNumSpecialChar}})" +
            ".{${this.minLength},})"
}