package com.example.megasena

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText: EditText = findViewById(R.id.edit_number)
        val textResult: TextView = findViewById(R.id.txt_result)
        val btn: Button = findViewById(R.id.btn_generate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        /*if (result != null){
            textResult.text = "Última aposta: $result"
        }*/

        result?.let {
            textResult.text = "Última aposta: $it"
        }


        btn.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, textResult)
        }

    }

    private fun numberGenerator(text:String, textResult: TextView) {
        if (text.isNotEmpty()){
            val qtd = text.toInt()
            if (qtd >= 6 && qtd <= 15){
                val numbers = mutableSetOf<Int>()
                val random = Random()

                while(true){
                    val number = random.nextInt(60)
                    numbers.add(number+1)
                    if (numbers.size==qtd)break
                }
                textResult.text = numbers.joinToString(" - ")

                /*val editor = prefs.edit()
                editor.putString("result", textResult.text.toString())
                editor.apply()*/

                prefs.edit().apply {
                    putString("result", textResult.text.toString())
                    apply()
                }

            }else{
                Toast.makeText(this, "informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, "informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
        }
    }
}