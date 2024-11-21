package com.example.myapplication_salarioneto

import android.os.Bundle
import android.widget.Button

import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class ResultadoActivity_main : AppCompatActivity() {
    private lateinit var btnRecalcular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnRecalcular = findViewById(R.id.btnReCalcular)
        // Obtener los datos pasados desde activity_main
        val salarioBruto = intent.getDoubleExtra("SALARIO_BRUTO", 0.0)
        val salarioNeto = intent.getDoubleExtra("SALARIO_NETO", 0.0)
        val retencionIRPF = intent.getDoubleExtra("RETENCION_IRPF", 0.0)
        val deducciones = intent.getDoubleExtra("DEDUCCIONES", 0.0)
        val salarioMensual = intent.getDoubleExtra("SALARIO_MENSUAL", 0.0)
        val decimalFormat = DecimalFormat("#.## €")

        // Mostrar los datos sus correspondientes campos
        findViewById<TextView>(R.id.SolucionSalarioBruto).text = decimalFormat.format(salarioBruto)
        findViewById<TextView>(R.id.SolucionSalarioNeto).text = decimalFormat.format(salarioNeto)
        findViewById<TextView>(R.id.SolucionIRPF).text = decimalFormat.format(retencionIRPF)
        findViewById<TextView>(R.id.SolucionDeducciones).text = decimalFormat.format(deducciones)
        findViewById<TextView>(R.id.SolucionpagaMes).text = decimalFormat.format(salarioMensual)
        btnRecalcular.setOnClickListener {
            finish()//nos devuelve a la pantalla anterior
        }
    }
}