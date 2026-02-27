package com.kommhotel.server.db.tables

import org.jetbrains.exposed.sql.*

object BookingTable : Table("bookings") {
    val id = varchar("id", 128)
    val guestId = varchar("guest_id", 128).references(UserTable.id)
    val roomId = varchar("room_id", 128)
    val checkInDate = varchar("check_in_date", 64)
    val checkOutDate = varchar("check_out_date", 64)
    val totalPrice = double("total_price")
    val status = varchar("status", 32)

    override val primaryKey = PrimaryKey(id)
}
