package com.example.myapplication_salarioneto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {


    //Vistas recogidas del xml
    private lateinit var cardEdad: CardView
    private lateinit var cardHijos: CardView
    private lateinit var cardSalario: CardView
    private lateinit var cardGrupo: CardView
    private lateinit var cardEstadoCivil: CardView
    private lateinit var cardDiscapacidad: CardView
    private lateinit var cardPaga: CardView

    //Campo de datos recogidos del xml
    private lateinit var datoEdad: TextView
    private lateinit var datoHijos: TextView
    private lateinit var datoSalarioBruto: TextInputEditText
    private lateinit var datoGrupoProfesional: Spinner
    private lateinit var datoEstadoCivil: Spinner
    private lateinit var datoDiscapacidad: Spinner
    private lateinit var datoPaga: TextView

    //campo accion botones recogidos del xml
    private lateinit var btnRestarEdad: FloatingActionButton
    private lateinit var btnSumarEdad: FloatingActionButton
    private lateinit var btnRestarHijos: FloatingActionButton
    private lateinit var btnSumarHijos: FloatingActionButton
    private lateinit var btnRestarPaga: FloatingActionButton
    private lateinit var btnSumarPaga: FloatingActionButton
    private lateinit var btnCalcular: Button

    //variables definidas como globales
    private var edad = 20
    private var hijos = 0
    private var numerodePagas = 12


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponentes()
        setupListeners()

    }

    private fun initComponentes() {
        this.cardEdad = findViewById(R.id.Edad)
        this.cardHijos = findViewById(R.id.hijos)
        this.cardSalario = findViewById(R.id.SalarioNeto)
        this.cardGrupo = findViewById(R.id.GrupoProfesional)
        this.cardEstadoCivil = findViewById(R.id.EstadoCivil)
        this.cardDiscapacidad = findViewById(R.id.GradoDiscapacidad)
        this.cardPaga = findViewById(R.id.Paga)
        //campos de datos, textview y texInput
        this.datoEdad = findViewById(R.id.datoEdad)
        this.datoHijos = findViewById(R.id.datoHijos)
        this.datoSalarioBruto = findViewById(R.id.datoSalarioBruto)
        this.datoGrupoProfesional = findViewById(R.id.datoGrupoProfesional)
        this.datoEstadoCivil = findViewById(R.id.datoEstadoCivil)
        this.datoDiscapacidad = findViewById(R.id.datoGradoDiscapacidad)
        this.datoPaga = findViewById(R.id.datoPaga)

        this.btnRestarEdad = findViewById(R.id.btnRestarEdad)
        this.btnSumarEdad = findViewById(R.id.btnSumarEdad)
        this.btnRestarHijos = findViewById(R.id.btnRestarHijos)
        this.btnSumarHijos = findViewById(R.id.btnSumarHijos)
        this.btnSumarPaga = findViewById(R.id.btnSumarPaga)
        this.btnRestarPaga = findViewById(R.id.btnRestarPaga)
        this.btnCalcular = findViewById(R.id.btnCalcular)


        // Inicializamos la edad en la TextView
        this.datoEdad.text = edad.toString()
        this.datoPaga.text = numerodePagas.toString()
    }

    private fun setupListeners() {

        // Botones EDAD
        btnSumarEdad.setOnClickListener {
            edad++
            datoEdad.text = edad.toString()
        }
        btnRestarEdad.setOnClickListener {
            if (edad > 16) edad--
            datoEdad.text = edad.toString()
        }
        // Botones HIJOS
        btnSumarHijos.setOnClickListener {
            hijos++
            datoHijos.text = hijos.toString()
        }
        btnRestarHijos.setOnClickListener {
            if (hijos > 0) hijos--
            datoHijos.text = hijos.toString()
        }
        //Botones nºpagas
        btnSumarPaga.setOnClickListener {
            numerodePagas++
            datoPaga.text = numerodePagas.toString()
        }
        btnRestarPaga.setOnClickListener {
            if (numerodePagas > 12) numerodePagas--
            datoPaga.text = numerodePagas.toString()
        }

        btnCalcular.setOnClickListener {
            val salarioNeto = calcularSalarioNeto()
            val retencionIRPF = calcularIRPF()
            val deducciones = calcularDeducciones()
            val salarioMes = calcularSalarioMes()
            val calculoSalarioBruto = datoSalarioBruto.text.toString().toDoubleOrNull() ?: 0.0
            val intent = Intent(this, ResultadoActivity_main::class.java).apply {
                putExtra("SALARIO_BRUTO", calculoSalarioBruto)
                putExtra("SALARIO_NETO", salarioNeto)
                putExtra("RETENCION_IRPF", retencionIRPF)
                putExtra("DEDUCCIONES", deducciones)
                putExtra("SALARIO_MENSUAL", salarioMes)
            }
            startActivity(intent)
        }
    }

    private fun calcularSalarioMes(): Double {
        val solucion = calcularSalarioNeto() / numerodePagas
        return solucion
    }

    private fun calcularIRPF(): Double {
        // Obtener los valores de salario, hijos y estado civil
        val salarioBruto = datoSalarioBruto.text.toString().toDoubleOrNull() ?: 0.0
        val hijos = hijos // Asegúrate de tener la variable hijos correctamente definida
        val estadoCivil =
            datoEstadoCivil.selectedItem.toString() // Obtener el estado civil seleccionado
        var retencionIRPF = 0.0

        // Calcular IRPF según las condiciones del salario
        if (salarioBruto < 12450) {
            retencionIRPF = salarioBruto * 0.19
        } else if (salarioBruto in 12450.0..20199.0) {
            retencionIRPF = salarioBruto * 0.24
        } else if (salarioBruto in 20200.0..35199.0) {
            retencionIRPF = salarioBruto * 0.30
        } else if (salarioBruto in 35200.0..59999.0) {
            retencionIRPF = salarioBruto * 0.37
        } else if (salarioBruto in 60000.0..299999.0) {
            retencionIRPF = salarioBruto * 0.45
        } else if (salarioBruto >= 300000) {
            retencionIRPF = salarioBruto * 0.47
        }

        // Ajustar IRPF según el número de hijos
        if (hijos == 1) {
            //  //con 1 hijo1 aplicacamos porcentajes segun rango de ingreso
            if (salarioBruto in 17644.0..18694.0) {
                retencionIRPF -= retencionIRPF * 0.05 // Descuento del 5% para grupo A con 1 hijo
            } else if (salarioBruto in 18130.0..19262.0) {
                retencionIRPF -= retencionIRPF * 0.04 // Descuento del 4% para grupo B con 1 hijo
            } else if (salarioBruto in 16342.0..16867.0) {
                retencionIRPF -= retencionIRPF * 0.03 // Descuento del 3% para grupo C con 1 hijo
            }
        } else if (hijos >= 2) {
            //con 2 hijos aplicacamos porcentajes segun rango de ingreso
            if (salarioBruto in 17644.0..18694.0) {
                retencionIRPF -= retencionIRPF * 0.10
            } else if (salarioBruto in 18130.0..19262.0) {
                retencionIRPF -= retencionIRPF * 0.08
            } else if (salarioBruto in 16342.0..16867.0) {
                retencionIRPF -= retencionIRPF * 0.06
            }
        }

        // Segun estado civil contemplando el ingreso +1500 o -1500 directamente en el scroll
        when (estadoCivil) {
            "Soltero", "Viudo", "Divorciado", "Separado legalmente" -> {
            }

            "Cónyuge con más de 1.500 €" -> {
            }

            "Cónyuge con menos de 1.500 €" -> {
                retencionIRPF -= retencionIRPF * 0.05 // Descuento del 5% si el cónyuge gana menos de 1.500 €

            }

            else -> {
                // Otras situaciones (Si es necesario, se puede hacer algo adicional)
            }
        }

        return retencionIRPF
    }

    private fun calcularDeducciones(): Double {
        val calculoSalarioBruto = datoSalarioBruto.text.toString().toDoubleOrNull() ?: 0.0

        val deducciones = 0.03 * calculoSalarioBruto

        return deducciones
    }

    private fun calcularSalarioNeto(): Double {
        val calculoSalarioBruto = datoSalarioBruto.text.toString().toDoubleOrNull() ?: 0.0
        val solucion =
            calculoSalarioBruto - calcularIRPF() - calcularDeducciones() - calcularDiscapacidad()
        return solucion
    }

    private fun calcularDiscapacidad(): Double {
        val discapacidad = datoDiscapacidad.selectedItem.toString()
        var solucion = 0.0
        when (discapacidad) {
            "Sin discapacidad" -> {
            }

            "Menos del 65% (sin asistencia)" -> {
                solucion = calcularIRPF() + 1000
            }

            "Menos del 65% (con asistencia)" -> {
                solucion = calcularIRPF() + 1500

            }

            "Mayor o igual al 65%" -> {

                solucion = calcularIRPF() + 3000
            }

            else -> {
                // Otras situaciones (Si es necesario, se puede hacer algo adicional)
            }


        }
        return solucion
    }


}



