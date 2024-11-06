package com.example.imcapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import java.text.DecimalFormat

class ImcCalculatorActivity : AppCompatActivity() {

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnAddWeight: FloatingActionButton
    private lateinit var btnSubtractAge: FloatingActionButton
    private lateinit var btnAddAge: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var tvAge: TextView
    private lateinit var btnCalcular: AppCompatButton

    private val isMaleSelected: Boolean = true
    private var weight: Int = 0
    private var age: Int = 0
    private var height: Int = 0

    companion object {
        const val IMC_KEY = "RESULT"
    }

    private fun initComponents() {
        viewMale = findViewById(R.id.viewmale)
        viewFemale = findViewById(R.id.viewfemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        btnAddWeight = findViewById(R.id.btnAddWeight)
        btnSubtractAge = findViewById(R.id.btnSubtractAge)
        btnAddAge = findViewById(R.id.btnAddAge)
        tvWeight = findViewById(R.id.tvWeight)
        tvAge = findViewById(R.id.tvAge)
        btnCalcular = findViewById(R.id.btnCalcular)
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners() {
        viewMale.setOnClickListener {
            setGenderColor(isMaleSelected)
        }
        viewFemale.setOnClickListener {
            setGenderColor(!isMaleSelected)
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            //tvHeight.text = value.toString()
            tvHeight.text = DecimalFormat("#.##").format(value) + " cm"
        }
        btnSubtractWeight.setOnClickListener {
            setWeight(1)
        }
        btnAddWeight.setOnClickListener {
            setWeight(2)
        }
        btnSubtractAge.setOnClickListener {
            setAge(1)
        }
        btnAddAge.setOnClickListener {
            setAge(2)
        }
        btnCalcular.setOnClickListener {
            height = tvHeight.text.toString().replace(" cm", "").toInt()
            navigate2result(calculateIMC())
        }
    }

    private fun navigate2result(resultado: Double) {
        val intentIRA: Intent = Intent(this, ImcResultActivity::class.java)
        intentIRA.putExtra(IMC_KEY, resultado)
        startActivity(intentIRA)
    }

    private fun calculateIMC(): Double {
        var alturaEnMetros = height / 100.0
        val resultado: Double = (weight.toDouble() / (alturaEnMetros * alturaEnMetros))
        return resultado
    }

    private fun setWeight(operacion: Int) {
        if (operacion == 1 && weight >= 1) {
            this.weight -= 1
            tvWeight.text = weight.toString()
        } else if (operacion == 2) {
            this.weight += 1
            tvWeight.text = weight.toString()
        }
    }

    private fun setAge(operacion: Int) {
        if (operacion == 1 && age >= 1) {
            this.age -= 1
            tvAge.text = age.toString()
        } else if (operacion == 2) {
            this.age += 1
            tvAge.text = age.toString()
        }
    }

    private fun getBackgroundColor(isMaleSelected: Boolean): Int {
        val colorReference = if (isMaleSelected) {
            R.color.bg_comp_sel
        } else {
            R.color.bg_comp
        }
        return ContextCompat.getColor(this, colorReference)
    }

    private fun setGenderColor(isMaleSelected: Boolean) {
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(!isMaleSelected))
    }

    private fun initUI() {
        setGenderColor(isMaleSelected)
        setWeight(0)
        setAge(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc_calculator)
        initComponents()
        initListeners()
        initUI()
    }


}