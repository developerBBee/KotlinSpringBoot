package com.book.manager.domain.service

import com.book.manager.data.repository.Book
import com.book.manager.data.repository.BookRepository
import com.book.manager.data.repository.BookWithRental
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class BookServiceTest {
    private val bookRepository = mock<BookRepository>()
    private val bookService = BookService(bookRepository)

    @Test
    fun `getList when book list is exist then return list`() {
        val book = Book(1, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val bookWithRental = BookWithRental(book, null)
        val expected = listOf(bookWithRental)

        // bookRepository.findAllWithRental()が呼ばれたら、expectedを返すように設定
        whenever(bookRepository.findAllWithRental()).thenReturn(expected)

        val result = bookService.getList()
        Assertions.assertThat(expected).isEqualTo(result)
    }
}