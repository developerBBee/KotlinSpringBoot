package com.book.manager.data.repository

interface UserRepository {
    fun find(email: String): User?
    fun find(id: Long): User?
}