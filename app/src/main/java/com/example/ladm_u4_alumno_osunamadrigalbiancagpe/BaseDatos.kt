package com.example.ladm_u4_alumno_osunamadrigalbiancagpe

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE DATOS(NOCTRL VARCHAR(8) PRIMARY KEY, NOMBRE VARCHAR(200))")
        db.execSQL("CREATE TABLE ASISTENCIAS(FECHA VARCHAR(10) PRIMARY KEY, HORA VARCHAR(2), MINUTOS VARCHAR(2))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //
    }
}