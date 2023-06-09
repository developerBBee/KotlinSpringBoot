package com.book.manager.data.repository

interface RentalRepository {
    fun startRental(rental: Rental)
    fun endRental(bookId: Long)
}