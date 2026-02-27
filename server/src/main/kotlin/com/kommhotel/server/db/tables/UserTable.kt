package com.kommhotel.server.db.tables

import org.jetbrains.exposed.sql.*

object UserTable : Table("users") {
    val id = varchar("id", 128)
    val firstName = varchar("first_name", 256)
    val lastName = varchar("last_name", 256)
    val email = varchar("email", 256)
    val phoneNumber = varchar("phone_number", 32)
    val password = varchar("password", 64)

    override val primaryKey = PrimaryKey(id)
}
