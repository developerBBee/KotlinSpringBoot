package com.book.manager.data.repository

data class BookWithRental(
    val book: Book,
    val rental: Rental?
) {
    // rentalが存在するかどうかで、貸出中かどうかを判定する
    // 貸出中(not null) -> isRental = true
    // 貸出中でない(null) -> isRental = false
    val isRental: Boolean
        get() = rental != null
}
