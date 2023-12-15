package com.example.eventticketing

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VERSION) {
        companion object{
            private val DATABASE_VERSION = 1
            private val DATABASE_NAME = "TicketingDatabase"
            private val TABLE_USER = "UserTable"
            private val TABLE_TICKET = "TicketTable"
            private val KEY_USERID = "UserID"
            private val KEY_EMAIL = "Email"
            private val KEY_PASSWORD = "Password"
            private val KEY_TICKETID = "TicketID"
            private val KEY_REFNUM = "ReferenceNum"
            private val KEY_ZONE = "Zone"
            private val KEY_PAYMENT = "Payment"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USERS_TABLE = ("CREATE TABLE " + TABLE_USER + "("
                + KEY_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_EMAIL + " TEXT NOT NULL, "
                + KEY_PASSWORD + " TEXT NOT NULL)")

        val CREATE_TICKETS_TABLE = ("CREATE TABLE " + TABLE_TICKET + "("
                + KEY_TICKETID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_REFNUM + " TEXT NOT NULL, "
                + KEY_ZONE + " TEXT NOT NULL, "
                + KEY_PAYMENT + " TEXT NOT NULL, "
                + KEY_EMAIL + " TEXT NOT NULL)")

        db?.execSQL(CREATE_USERS_TABLE)
        db?.execSQL(CREATE_TICKETS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_USER)
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_TICKET)
        onCreate(db)
    }

    // TODO: Create fetch_user function
    @SuppressLint("Range")
    fun fetchUser(email:String):UserModelClass? {
        val selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_EMAIL + "='" + email + "'"
        val db = this.readableDatabase
        var cursor:Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            return null
        }

        var userEmail: String
        var userPassword: String

        if(cursor != null && cursor.moveToFirst()) {
            userEmail = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
            userPassword = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
            val user = UserModelClass(userEmail = userEmail, userPassword = userPassword)

            db.close()
            cursor.close()
            return user
        }

        db.close()
        cursor.close()
        return null
    }

    // TODO: Create add_user function
    fun addUser(user: UserModelClass):Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_EMAIL, user.userEmail)
        contentValues.put(KEY_PASSWORD, user.userPassword)

        val success = db.insert(TABLE_USER, null, contentValues)
        db.close()
        return success
    }

    // TODO: Create add_ticket function
    fun addTicket(ticket: TicketModelClass):Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_REFNUM, ticket.ticketRefNum)
        contentValues.put(KEY_ZONE, ticket.ticketZone)
        contentValues.put(KEY_PAYMENT, ticket.paymentMethod)
        contentValues.put(KEY_EMAIL, ticket.userEmail)

        val success = db.insert(TABLE_TICKET, null, contentValues)
        db.close()
        return success
    }

    // TODO: Create fetch_ticket function
    @SuppressLint("Range")
    fun fetchTicket(email: String):ArrayList<String>? {
        val selectQuery = "SELECT * FROM " + TABLE_TICKET + " WHERE " + KEY_EMAIL + "='" + email + "'"
        val db = this.readableDatabase
        var cursor:Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            return null
        }

        val list = ArrayList<String>()

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                list.add(cursor.getString(cursor.getColumnIndex(KEY_REFNUM)))
                cursor.moveToNext()
            }
            db.close()
            cursor.close()
            return list
        }

        db.close()
        cursor.close()
        return null
    }

}