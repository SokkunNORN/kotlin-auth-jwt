package me.auth.com.authentication.command

enum class GenderEnum(gender: String) {
    MALE("Male"),
    FEMALE("Female")
}

enum class StatusEnum(val id: Long, val code: String) {
    ACTIVE(1, "Active"),
    INACTIVE(1, "Inactive")
}

enum class RoleTypeEnum {
    NBC,
    FI
}
