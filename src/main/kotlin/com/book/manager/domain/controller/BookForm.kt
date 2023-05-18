package com.book.manager.domain.controller

import com.book.manager.data.repository.Book
import com.book.manager.data.repository.BookWithRental
import com.book.manager.data.repository.Rental
import java.time.LocalDate
import java.time.LocalDateTime

data class GetBookListResponse(val bookList: List<BookInfo>)

data class BookInfo(
    val id: Long,
    val title: String,
    val author: String,
    val isRental: Boolean
) {
    constructor(model: BookWithRental) : this(
        id = model.book.id,
        title = model.book.title,
        author = model.book.author,
        isRental = model.isRental
    )
}

data class GetBookDetailResponse(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate,
    val rentalInfo: RentalInfo?
) {
    constructor(model: BookWithRental) : this(
        id = model.book.id,
        title = model.book.title,
        author = model.book.author,
        releaseDate = model.book.releaseDate,
        rentalInfo = model.rental?.let { RentalInfo(model.rental) }
    )
}

data class RentalInfo(
    val userId: Long,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime
) {
    constructor(rental: Rental) : this(
        userId = rental.userId,
        rentalDatetime = rental.rentalDatetime,
        returnDeadline = rental.returnDeadline
    )
}

data class RegisterBookRequest(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
) {
    fun toBook(): Book = Book(
        id = id,
        title = title,
        author = author,
        releaseDate = releaseDate
    )
}