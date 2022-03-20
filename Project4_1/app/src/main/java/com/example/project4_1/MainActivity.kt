package com.example.project4_1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    lateinit var edit1 : EditText; lateinit var edit2 : EditText
    lateinit var btnAdd : Button; lateinit var btnSub : Button
    lateinit var btnMul : Button; lateinit var btnDiv : Button
    lateinit var btnRem : Button
    lateinit var textResult : TextView
    lateinit var num1 : String; lateinit var num2 : String
    var result : Double? = null
    val df = DecimalFormat("#.##")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "2018110006 김민웅"

        edit1 = findViewById<EditText>(R.id.Edit1)
        edit2 = findViewById<EditText>(R.id.Edit2)
        btnAdd = findViewById<Button>(R.id.BtnAdd)
        btnSub = findViewById<Button>(R.id.BtnSub)
        btnMul = findViewById<Button>(R.id.BtnMul)
        btnDiv = findViewById<Button>(R.id.BtnDiv)
        btnRem = findViewById<Button>(R.id.BtnRem)
        textResult = findViewById<TextView>(R.id.TextResult)

        btnAdd.setOnClickListener { view ->
            num1 = edit1.text.toString()
            num2 = edit2.text.toString()
            if (num1 == "" || num2 == ""){
                returnError(null)
            }
            else {
                result = num1.toDouble() + num2.toDouble()
                textResult.text = "계산 결과 : " + result.toString()
            }
        }

        btnSub.setOnClickListener { view ->
            num1 = edit1.text.toString()
            num2 = edit2.text.toString()
            if (num1 == "" || num2 == ""){
                returnError(null)
                false
            }
            else {
                result = num1.toDouble() - num2.toDouble()
                textResult.text = "계산 결과 : " + result.toString()
            }
        }

        btnMul.setOnClickListener { view ->
            num1 = edit1.text.toString()
            num2 = edit2.text.toString()
            if (num1 == "" || num2 == ""){
                returnError(null)
                false
            }
            else {
                result = num1.toDouble() * num2.toDouble()
                textResult.text = "계산 결과 : " + result.toString()
            }
        }

        btnDiv.setOnClickListener { view ->
            num1 = edit1.text.toString()
            num2 = edit2.text.toString()
            if (num1 == "" || num2 == ""){
                returnError(null)
                false
            }
            else if(num2 == "0"){
                returnError("0으로 나눌 수 없음.")
            }
            else {
                result = num1.toDouble() / num2.toDouble()
                textResult.text = "계산 결과 : " + result.toString()
            }
        }

        btnRem.setOnClickListener { view ->
            num1 = edit1.text.toString()
            num2 = edit2.text.toString()
            if (num1 == "" || num2 == ""){
                returnError(null)
                false
            }
            else if(num2 == "0"){
                returnError("0으로 나눌 수 없음.")
            }
            else {
                result = num1.toDouble() % num2.toDouble()
                textResult.text = "계산 결과 : " + result.toString()
            }
        }



    }

    fun returnError(str : String?){
        if(str != null){
            Toast.makeText(this@MainActivity, str, Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this@MainActivity, "숫자를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }
}