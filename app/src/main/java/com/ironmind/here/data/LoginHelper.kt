package com.ironmind.here.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Environment
import java.io.File

fun loginUser(email: String, password: String): Boolean {
    return try {
        val dbPath = "/data/data/com.ironmind.here/files/applipresence.db"
        val dbFile = File(dbPath)
        if (!dbFile.exists()) return false

        val db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
        val cursor = db.rawQuery(
            "SELECT * FROM utilisateurs WHERE email = ? AND motDePasse = ?",
            arrayOf(email, password)
        )

        val success = cursor.count > 0
        cursor.close()
        db.close()
        success
    } catch (e: SQLiteException) {
        e.printStackTrace()
        false
    }
}
