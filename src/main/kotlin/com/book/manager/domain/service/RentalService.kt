package com.book.manager.domain.service

import com.book.manager.data.repository.BookRepository
import com.book.manager.data.repository.Rental
import com.book.manager.data.repository.RentalRepository
import com.book.manager.data.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalStateException
import java.time.LocalDateTime

private const val RENTAL_TERM_DAYS = 14L
@Service
class RentalService(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val rentalRepository: RentalRepository
) {
    @Transactional
    fun startRental(bookId: Long, userId: Long) {
        userRepository.find(userId)
            ?: throw IllegalArgumentException("存在しないユーザID:$userId")
        val book = bookRepository.findWithRental(bookId)
            ?: throw IllegalArgumentException("存在しない書籍ID:$bookId")
        if (book.isRental) throw IllegalStateException("書籍ID:$bookId は貸出中です")
        val rentalDatetime = LocalDateTime.now()
        val returnDeadline = rentalDatetime.plusDays(RENTAL_TERM_DAYS)
        val rental = Rental(
            bookId = bookId,
            userId = userId,
            rentalDatetime = rentalDatetime,
            returnDeadline = returnDeadline
        )
        rentalRepository.startRental(rental)
    }

    @Transactional
    fun endRental(bookId: Long, userId: Long) {
        userRepository.find(userId)
            ?: throw IllegalArgumentException("存在しないユーザID:$userId")
        val book = bookRepository.findWithRental(bookId)
            ?: throw IllegalArgumentException("存在しない書籍ID:$bookId")
        if (!book.isRental) throw IllegalStateException("書籍ID:$bookId は貸出されていません")
        if (book.rental!!.userId != userId)
            throw IllegalStateException("書籍ID:$bookId の貸出先はユーザID:$userId ではありません")
        rentalRepository.endRental(bookId)
    }
}