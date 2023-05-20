package com.book.manager.data.repository

import com.book.manager.infrastructure.database.mapper.RentalMapper
import com.book.manager.infrastructure.database.mapper.insert
import com.book.manager.infrastructure.database.record.RentalRecord
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class RentalRepositoryImpl(
    private val rentalMapper: RentalMapper
) : RentalRepository {
    override fun startRental(rental: Rental) {
        rentalMapper.insert(toRecord(rental))
    }
    private fun toRecord(model: Rental): RentalRecord {
        return RentalRecord(
            bookId = model.bookId,
            userId = model.userId,
            rentalDatetime = model.rentalDatetime,
            returnDeadline = model.returnDeadline,
        )
    }
}