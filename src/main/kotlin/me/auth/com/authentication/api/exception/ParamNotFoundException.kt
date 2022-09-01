package me.auth.com.authentication.api.exception.handler

class ParamNotFoundException(field: String) : RuntimeException(field)