package com.book.manager.domain.service

import com.book.manager.data.repository.Book
import com.book.manager.data.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class AdminBookService (
    private val bookRepository: BookRepository
) {
    @Transactional
    fun register(book: Book) {
        bookRepository.findWithRental(book.id)?.let { throw IllegalArgumentException("すでに存在する書籍ID:${book.id}") }
        bookRepository.register(book)
    }
}