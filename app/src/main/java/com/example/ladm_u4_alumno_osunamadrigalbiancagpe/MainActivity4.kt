package com.example.ladm_u4_alumno_osunamadrigalbiancagpe

import android.R
import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.ladm_u4_alumno_osunamadrigalbiancagpe.databinding.ActivityMain4Binding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity4 : AppCompatActivity() {
    lateinit var binding: ActivityMain4Binding

    val vector = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lblDate.text = fechaActual()
        mostrarAsistencia()
        llenarLista()

        binding.btnDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }

    fun llenarLista(){
        vector.clear()
        val lista = Registros(this).obtenerAsistencias()

        if(lista.size>0) {
            (0..lista.size - 1).forEach {
                val asist = lista[it]
                vector.add(
                    "Fecha: ${asist.fecha}\n" +
                            "Hora: ${asist.hora}:${asist.minutos}"
                )
            }
        }

        binding.lvAsistencia.adapter = ArrayAdapter<String>(this,
            R.layout.simple_list_item_1, vector)
    }

    private fun mostrarAsistencia() {
        val asist = Registros(this).buscarAsistencia(binding.lblDate.text.toString())
        if(asist.fecha==""){
            binding.lblAsistencia.setText("No tiene asistencia " +
                    "registrada el día ${binding.lblDate.text}")
            binding.lblAsistencia.setTextColor(Color.parseColor("#F44336"))
        }else {
            binding.lblAsistencia.setText("Asistencia registrada a " +
                    "las ${asist.hora}:${asist.minutos} el día ${asist.fecha}")
            binding.lblAsistencia.setTextColor(Color.parseColor("#4CAF50"))
        }
    }

    fun showDatePicker(){
        val dateSelected = binding.lblDate.text.toString().split("-")
        val d = dateSelected[0].toInt()
        val m = dateSelected[1].toInt()
        val y = dateSelected[2].toInt()

        val datePicker = DatePickerFragment{
                day, month, year -> onDateSelected(day, month, year)
        }
        datePicker.dia = d
        datePicker.mes = m-1
        datePicker.anio = y
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        val m = month+1
        if(m<10 && day<10){
            binding.lblDate.text = "0${day}-0${m}-${year}"
        } else if(m<10) {
            binding.lblDate.text = "${day}-0${m}-${year}"
        } else if(day<10) {
            binding.lblDate.text = "0${day}-${m}-${year}"
        } else {
            binding.lblDate.text = "${day}-${m}-${year}"
        }
        mostrarAsistencia()
    }

    @SuppressLint("NewApi")
    fun fechaActual(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)
        return formatted
    }
}