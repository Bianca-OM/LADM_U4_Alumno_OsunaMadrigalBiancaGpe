package com.example.ladm_u4_alumno_osunamadrigalbiancagpe

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Registros (e: Context) {
    private val este = e
    private val base = "registroasistencia"
    var noctrl = ""
    var nombre = ""
    var fecha = ""
    var hora = ""
    var minutos = ""
    private var err = ""

    fun insertarDatos() : Boolean{
        val basedatos = BaseDatos(este, base, null, 1)
        err = ""
        try {
            val tabla = basedatos.writableDatabase
            val datos = ContentValues()

            datos.put("NOCTRL", noctrl)
            datos.put("NOMBRE", nombre)

            val respuesta = tabla.insert("DATOS", null, datos)
            if(respuesta == -1L){
                AlertDialog.Builder(este)
                    .setTitle("Error")
                    .setMessage("Respuesta = -1\n" +
                            "Verifique que el registro no se repita")
                    .show()
                return false
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
            AlertDialog.Builder(este)
                .setMessage("Error: ${this.err}")
                .show()
            return false
        } finally {
            basedatos.close()
        }
        return true
    }

    fun insertarAsistencia() : Boolean{
        val basedatos = BaseDatos(este, base, null, 1)
        err = ""
        try {
            val tabla = basedatos.writableDatabase
            val datos = ContentValues()

            datos.put("FECHA", fecha)
            datos.put("HORA", hora)
            datos.put("MINUTOS", minutos)

            val respuesta = tabla.insert("ASISTENCIAS", null, datos)
            if(respuesta == -1L){
                AlertDialog.Builder(este)
                    .setTitle("Error")
                    .setMessage("Respuesta = -1\n" +
                            "Verifique que el registro no se repita")
                    .show()
                return false
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
            AlertDialog.Builder(este)
                .setMessage("Error: ${this.err}")
                .show()
            return false
        } finally {
            basedatos.close()
        }
        return true
    }

    fun obtenerDatos() : Registros {
        val basedatos = BaseDatos(este, base, null, 1)
        err = ""
        val dat = Registros(este)

        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM DATOS"

            val cursor = tabla.rawQuery(SQLSELECT, null)
            if(cursor.moveToFirst()){
                dat.noctrl = cursor.getString(0)
                dat.nombre = cursor.getString(1)
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
            AlertDialog.Builder(este)
                .setMessage("Error: ${this.err}")
                .show()
        } finally {
            basedatos.close()
        }
        return dat
    }

    fun obtenerAsistencias() : ArrayList<Registros> {
        val basedatos = BaseDatos(este, base, null, 1)
        err = ""
        val asistencias = ArrayList<Registros>()

        try{
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ASISTENCIAS"

            val cursor = tabla.rawQuery(SQLSELECT, null)
            if(cursor.moveToFirst()){
                do {
                    val asist = Registros(este)
                    asist.fecha = cursor.getString(0)
                    asist.hora = cursor.getString(1)
                    asist.minutos = cursor.getString(2)
                    asistencias.add(asist)
                }while (cursor.moveToNext())
            }
        } catch (err: SQLiteException) {
            this.err = err.message!!
            AlertDialog.Builder(este)
                .setMessage("Error: ${this.err}")
                .show()
        } finally {
            basedatos.close()
        }
        return asistencias
    }

    fun buscarAsistencia(fechaBuscar: String) : Registros {
        val basedatos = BaseDatos(este, base, null, 1)
        err = ""
        val fb = fechaBuscar
        val asist = Registros(este)

        try {
            val tabla = basedatos.readableDatabase
            val SQLSELECT = "SELECT * FROM ASISTENCIAS WHERE FECHA=?"

            val cursor = tabla.rawQuery(SQLSELECT, arrayOf(fb))
            if(cursor.moveToFirst()){
                asist.fecha = cursor.getString(0)
                asist.hora = cursor.getString(1)
                asist.minutos = cursor.getString(2)
            }

        } catch (err: SQLiteException) {
            this.err = err.message!!
            AlertDialog.Builder(este)
                .setMessage("Error: ${this.err}")
                .show()
        } finally {
            basedatos.close()
        }
        return asist
    }
}