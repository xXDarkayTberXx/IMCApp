package com.example.imcapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.imcapp.ImcCalculatorActivity.Companion.IMC_KEY
import java.text.DecimalFormat

class ImcResultActivity : AppCompatActivity() {

    private lateinit var tvVeredicto: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvImcCalculado: TextView
    private lateinit var btnRecalcular: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_result)

        recibirImc()
        initComponents()
        initListeners()
        initUI()
    }

    private fun recibirImc(): Double {
        val imc = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        return imc
    }

    private fun initComponents() {
        tvVeredicto = findViewById(R.id.tvVeredicto)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvImcCalculado = findViewById(R.id.tvImcCalculado)
        btnRecalcular = findViewById(R.id.btnRecalcular)
    }

    private fun initListeners() {
        btnRecalcular.setOnClickListener { finish() }
    }

    private fun initUI() {
        formatearTextos()
    }

    private fun formatearTextos() {

        when (recibirImc()) {
            in 0.01..18.5 -> {
                tvVeredicto.setTextColor(tvVeredicto.getContext().getColor(R.color.infrapeso))
                tvVeredicto.setText(R.string.Infrapeso)
                tvDescripcion.setText(R.string.InfrapesoExplicado)
            }

            in 18.5..24.99 -> {
                tvVeredicto.setTextColor(tvVeredicto.getContext().getColor(R.color.normal))
                tvVeredicto.setText(R.string.Normal)
                tvDescripcion.setText(R.string.NormalExplicado)
            }

            in 25.0..29.99 -> {
                tvVeredicto.setTextColor(tvVeredicto.getContext().getColor(R.color.sobrepeso))
                tvVeredicto.setText(R.string.Sobrepeso)
                tvDescripcion.setText(R.string.SobrepesoExplicado)
            }

            in 30.0..Double.MAX_VALUE -> {
                tvVeredicto.setTextColor(tvVeredicto.getContext().getColor(R.color.obesidad))
                tvVeredicto.setText(R.string.Obesidad)
                tvDescripcion.setText(R.string.ObesidadExplicado)
            }
        }

        tvImcCalculado.setText(DecimalFormat("#.##").format(recibirImc()))

    }

}