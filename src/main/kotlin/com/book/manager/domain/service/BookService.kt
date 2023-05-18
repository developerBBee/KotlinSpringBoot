package com.book.manager.domain.service

import com.book.manager.data.repository.BookRepository
import com.book.manager.data.repository.BookWithRental
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRental> = bookRepository.findAllWithRental()
    fun getDetail(bookId: Long): BookWithRental = bookRepository.findWithRental(bookId)
        ?: throw IllegalArgumentException("存在しない書籍ID:$bookId")
}