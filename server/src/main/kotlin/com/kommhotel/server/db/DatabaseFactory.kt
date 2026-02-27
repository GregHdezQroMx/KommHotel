package com.kommhotel.server.db

import com.kommhotel.server.db.tables.BookingTable
import com.kommhotel.server.db.tables.UserTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        // NOTE: Connection details should be moved to a configuration file in a real application.
        val jdbcURL = "jdbc:postgresql://localhost:5432/kommhotel?user=ghetz&password=password"

        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(UserTable, BookingTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend Transaction.() -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
