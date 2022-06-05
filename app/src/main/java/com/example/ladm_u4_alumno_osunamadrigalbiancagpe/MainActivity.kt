package com.example.ladm_u4_alumno_osunamadrigalbiancagpe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.ladm_u4_alumno_osunamadrigalbiancagpe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var datos = Registros(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTest.setOnClickListener {
            val vtnTest = Intent(this, MainActivity5::class.java)
            startActivity(vtnTest)
        }

        binding.btnDatos.setOnClickListener {
            datos = datos.obtenerDatos()
            if(datos.noctrl==""){
                val vtnDatos = Intent(this, MainActivity3::class.java)
                startActivity(vtnDatos)
            }else{
                AlertDialog.Builder(this).setMessage("SUS DATOS YA HAN SIDO REGISTRADOS Y NO SE PUEDEN CAMBIAR").show()
            }
        }

        binding.btnAsistencia.setOnClickListener {
            datos = datos.obtenerDatos()
            if(datos.noctrl==""){
                AlertDialog.Builder(this).setMessage("DEBE REGISTRAR SUS DATOS PARA PODER REGISTRAR SU ASISTENCIA").show()
            }else{
                val vtnAsistencia = Intent(this, MainActivity2::class.java)
                vtnAsistencia.putExtra("NC", datos.noctrl)
                vtnAsistencia.putExtra("NAME", datos.nombre)
                startActivity(vtnAsistencia)
            }
        }

        binding.btnConsultar.setOnClickListener {
            val vtnConsultar = Intent(this, MainActivity4::class.java)
            startActivity(vtnConsultar)
        }
    }
}