package com.book.manager.domain.service

import com.book.manager.data.repository.*
import com.book.manager.domain.enum.RoleType
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException
import java.time.LocalDate
import java.time.LocalDateTime

internal class RentalServiceTest {
    private val userRepository = mock<UserRepository>()
    private val bookRepository = mock<BookRepository>()
    private val rentalRepository = mock<RentalRepository>()

    private val rentalService = RentalService(
        bookRepository = bookRepository,
        userRepository = userRepository,
        rentalRepository = rentalRepository
    )

    @Test
    fun `endRental when book is rental then delete to rental`() {
        val userId = 100L
        val bookId = 1000L
        val user = User(userId, "test@test.com", "test", "kotlin", RoleType.USER)
        val book = Book(bookId, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val rental = Rental(bookId, userId, LocalDateTime.now(), LocalDateTime.MAX)
        val bookWithRental = BookWithRental(book, rental)

        // 引数にany()を指定することで、引数の値に関わらずthenReturn()に指定した値を返すようになる
        whenever(userRepository.find(any() as Long)).thenReturn(user)
        whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)

        rentalService.endRental(bookId = bookId, userId = userId)

        // verify()で、指定したメソッドが呼ばれたかどうかを検証できる
        verify(userRepository).find(userId) // userIdでユーザーを検索しているか
        verify(bookRepository).findWithRental(bookId) // bookIdで書籍を検索しているか
        verify(rentalRepository).endRental(bookId) // bookIdで貸出を終了しているか
    }

    @Test
    fun `endRental when book is not rental then throw exception`() {
        val userId = 100L
        val bookId = 1000L
        val user = User(userId, "test@test.com", "test", "kotlin", RoleType.USER)
        val book = Book(bookId, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val bookWithRental = BookWithRental(book, null)

        whenever(userRepository.find(any() as Long)).thenReturn(user)
        whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)
        val exception = Assertions.assertThrows(IllegalStateException::class.java) {
            rentalService.endRental(bookId = bookId, userId = userId)
        }

        assertThat(exception.message).isEqualTo("書籍ID:$bookId は貸出されていません")

        verify(userRepository).find(userId)
        verify(bookRepository).findWithRental(bookId)
        verify(rentalRepository, times(0)).endRental(any())
    }
}