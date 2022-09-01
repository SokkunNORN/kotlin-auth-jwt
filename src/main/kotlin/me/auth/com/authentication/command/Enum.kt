package me.auth.com.authentication.command

enum class GenderEnum(gender: String) {
    MALE("Male"),
    FEMALE("Female")
}

enum class StatusEnum(id: Long, name: String) {
    ACTIVE(1, "Active"),
    INACTIVE(1, "Inactive")
}
