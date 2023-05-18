package com.book.manager.data.repository

import com.book.manager.infrastructure.database.mapper.*
import com.book.manager.infrastructure.database.record.BookRecord
import com.book.manager.infrastructure.database.record.BookWithRentalRecord
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class BookRepositoryImpl(
    private val bookWithRentalMapper: BookWithRentalMapper,
    private val bookMapper: BookMapper
) : BookRepository {
    override fun findAllWithRental(): List<BookWithRental> {
        return bookWithRentalMapper.select()
            .map { toModel(it) }
    }

    override fun findWithRental(id: Long): BookWithRental? {
        return bookWithRentalMapper.selectByPrimaryKey(id)
            ?.let { toModel(it) }
    }

    override fun register(book: Book) {
        bookMapper.insert(toRecord(book))
    }

    private fun toModel(record: BookWithRentalRecord): BookWithRental {
        return BookWithRental(
            book = Book(
                id = record.id!!,
                title = record.title!!,
                author = record.author!!,
                releaseDate = record.releaseDate!!
            ),
            rental = record.userId?.let {
                Rental(
                    bookId = record.id!!,
                    userId = record.userId!!,
                    rentalDatetime = record.rentalDatetime!!,
                    returnDeadline = record.returnDeadline!!
                )
            }
        )
    }

    private fun toRecord(model: Book): BookRecord {
        return BookRecord(
            id = model.id,
            title = model.title,
            author = model.author,
            releaseDate = model.releaseDate
        )
    }
}