package com.example.nimble.database

import android.util.Log
import com.example.nimble.errorMsg
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import kotlin.concurrent.thread

/**
 * use Database.connected to see the status of the Database object
 */
object Database {
    private val LOGTAG = "db" /// for Logging

    private val db_link = "phoebe.hosterion.net"
    private val db_port = "1500"
    private val db_name = "DB_GHT"
    private val db_user = "admin_GHT"
    private val db_password = "V*Jt8WaZA\$crz@PWu2cHF&tA26sHNr"

    private val connectionString = "jdbc:jtds:sqlserver://" + db_link + ':' + db_port + ';' +
            "databaseName=" + db_name + ';' +
            "user=" + db_user + ';' +
            "password=" + db_password + ';' +
            "integratedSecurity=false;" +
            "loginTimeout=30;";

    private lateinit var connection: Connection

    /**
     * connected == 0   -   no connection has been attempted
     * connected == 1   -   connection succeeded
     * connected == 2   -   connection failed
     */
    var connected = 0

    fun connect () {
        try {
            connection = DriverManager.getConnection(connectionString) as Connection
            connected = 1
        } catch (e: Exception) {
            Log.e(LOGTAG, "connect() - Database failed to connect", e)
            connected = 2
        }
    }

    fun disconnect () {
        if (connected == 1) {
            connection.close()
        } else {
            Log.i(LOGTAG, "disconnect() - No connection to close, database has never been connected. Database.connected = $connected")
        }
    }

    /**
     * returns true if the connection succeeded, returns false otherwise
     */
    fun isConnected(): Boolean {
        return connected == 1
    }

    /**
     * runs a SELECT statement that doesn't change the db
     * returns the resultSet (returns null in case of error)
     */
    fun runQuery (query: String): ResultSet? {
        if (!isConnected()) {
            Log.e(LOGTAG, "runQuery() - Database is not connected")
            return null
        }

        try {
            return connection.createStatement().executeQuery(query)
        } catch (e: Exception) {
            Log.e(LOGTAG, "runQuery() - Cannot execute query (query == '$query')", e)
            return null
        }
    }

    /**
     * runs a non-SELECT statement which changes the db and returns an int (number of rows affected)
     * returns -1 in case of failure
     */
    fun runUpdate (update: String): Int {
        if (!isConnected()) {
            Log.e(LOGTAG, "runUpdate() - Database is not connected")
            return -1
        }

        try {
            return connection.createStatement().executeUpdate(update)
        } catch (e: Exception) {
            Log.e(LOGTAG, "runUpdate() - Cannot execute update (update == '$update')", e)
            return -1
        }
    }

    /**
     * DEBUG logs all the elements in the specified table
     */
    fun debugPrintTable (table: String) {
        /// get nr of columns in table
        var nrColStr = runQuery("""
            SELECT Count(*) FROM INFORMATION_SCHEMA.Columns WHERE TABLE_NAME = '$table'
        """.trimIndent())
        nrColStr!!.next()
        val nrCol = nrColStr.getString(1).toInt()

        /// get values in table
        val res = runQuery("""
            SELECT * FROM $table
        """.trimIndent())

        /// print values
        var outstr = " \n"
        while (res!!.next()) {
            for (i: Int in 1..nrCol)
                outstr += res.getString(i) + ' '
            outstr += '\n'
        }
        Log.d(LOGTAG, outstr)
    }
}