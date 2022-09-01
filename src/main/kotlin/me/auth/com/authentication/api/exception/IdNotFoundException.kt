package me.auth.com.authentication.api.exception.handler

class IdNotFoundException(name: String, id: Long) : RuntimeException("The $name id[$id]")