package com.example.ladm_u4_alumno_osunamadrigalbiancagpe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.ladm_u4_alumno_osunamadrigalbiancagpe.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener {
            registrar()
        }

        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }

    fun registrar() {
        val nc = binding.txtNctrl.text.toString()
        val nombre = binding.txtNombre.text.toString()

        if(nc == "" || nombre == ""){
            AlertDialog.Builder(this)
                .setTitle("CAMPOS VACIOS")
                .setMessage("Favor de verificar campos")
                .show()
        }else if(nc.length>8 || nombre.length>200){
            AlertDialog.Builder(this)
                .setTitle("LONGITUD EXCEDIDA")
                .setMessage("No. control no puede contener más de 8 caracteres\n" +
                        "Nombre no puede contener más de 200 caracteres")
                .show()
        }else{
            val dat = Registros(this)
            dat.noctrl = nc
            dat.nombre = nombre
            if(dat.insertarDatos()) {
                AlertDialog.Builder(this).setMessage("DATOS REGISTRADOS").show()
                finish()
            }
        }
    }
}