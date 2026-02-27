package com.kommhotel.server.repository

import com.kommhotel.server.db.DatabaseFactory.dbQuery
import com.kommhotel.server.db.tables.UserTable
import com.kommhotel.shared.model.Guest
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

interface UserRepository {
    suspend fun createUser(guest: Guest, passwordHash: String): Guest?
    suspend fun findUserByEmail(email: String): Pair<Guest, String>?
}

class UserRepositoryImpl : UserRepository {
    override suspend fun createUser(guest: Guest, passwordHash: String): Guest? = dbQuery {
        val insertStatement = UserTable.insert {
            it[UserTable.id] = guest.id
            it[UserTable.firstName] = guest.firstName
            it[UserTable.lastName] = guest.lastName
            it[UserTable.email] = guest.email
            it[UserTable.phoneNumber] = guest.phoneNumber
            it[UserTable.password] = passwordHash
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::toGuest)
    }

    override suspend fun findUserByEmail(email: String): Pair<Guest, String>? = dbQuery {
        UserTable.select { UserTable.email eq email }
            .map { toGuest(it) to it[UserTable.password] }
            .singleOrNull()
    }

    private fun toGuest(row: ResultRow): Guest = Guest(
        id = row[UserTable.id],
        firstName = row[UserTable.firstName],
        lastName = row[UserTable.lastName],
        email = row[UserTable.email],
        phoneNumber = row[UserTable.phoneNumber]
    )
}