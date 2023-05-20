package com.book.manager.domain.service

import com.book.manager.data.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository
) {
    fun findUser(email: String) = userRepository.find(email)
}