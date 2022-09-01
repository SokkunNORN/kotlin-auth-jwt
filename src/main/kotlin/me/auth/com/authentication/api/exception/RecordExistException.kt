package me.auth.com.authentication.api.exception.handler

class RecordExistException(field_id: String) : RuntimeException(field_id)