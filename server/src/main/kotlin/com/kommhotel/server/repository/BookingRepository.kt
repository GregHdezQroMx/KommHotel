package com.kommhotel.server.repository

import com.kommhotel.server.db.DatabaseFactory.dbQuery
import com.kommhotel.server.db.tables.BookingTable
import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.model.BookingStatus
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

interface BookingRepository {
    suspend fun createBooking(booking: Booking): Booking?
    suspend fun getBookingsByUserId(userId: String): List<Booking>
}

class BookingRepositoryImpl : BookingRepository {
    override suspend fun createBooking(booking: Booking): Booking? = dbQuery {
        val insertStatement = BookingTable.insert {
            it[id] = booking.id
            it[guestId] = booking.guestId
            it[roomId] = booking.roomId
            it[checkInDate] = booking.checkInDate
            it[checkOutDate] = booking.checkOutDate
            it[totalPrice] = booking.totalPrice
            it[status] = booking.status.name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toBooking)
    }

    override suspend fun getBookingsByUserId(userId: String): List<Booking> = dbQuery {
        BookingTable.select { BookingTable.guestId eq userId }
            .map(::toBooking)
    }

    private fun toBooking(row: ResultRow): Booking = Booking(
        id = row[BookingTable.id],
        guestId = row[BookingTable.guestId],
        roomId = row[BookingTable.roomId],
        checkInDate = row[BookingTable.checkInDate],
        checkOutDate = row[BookingTable.checkOutDate],
        totalPrice = row[BookingTable.totalPrice],
        status = BookingStatus.valueOf(row[BookingTable.status])
    )
}