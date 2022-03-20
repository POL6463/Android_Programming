package com.example.project4_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var text1 : TextView; lateinit var text2 : TextView
    lateinit var swtAgree : Switch; lateinit var rGroup1 : RadioGroup
    lateinit var rdoOreo : RadioButton; lateinit var rdoPie : RadioButton
    lateinit var rdoQ : RadioButton; lateinit var btnEnd : Button
    lateinit var imgOs : ImageView; lateinit var btnRst : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "2018110006 김민웅"

        text1 = findViewById<TextView>(R.id.Text1)
        swtAgree = findViewById<Switch>(R.id.SwtAgree)
        text2 = findViewById<TextView>(R.id.Text2)
        rGroup1 = findViewById<RadioGroup>(R.id.Rgroup1)
        rdoOreo = findViewById<RadioButton>(R.id.RdoOreo)
        rdoPie = findViewById<RadioButton>(R.id.RdoOreo)
        rdoQ = findViewById<RadioButton>(R.id.RdoOreo)
        btnRst = findViewById<Button>(R.id.BtnRst)
        btnEnd = findViewById<Button>(R.id.BtnEnd)
        imgOs = findViewById<ImageView>(R.id.ImgOs)

        swtAgree.setOnCheckedChangeListener { compoundButton, b ->

            if(swtAgree.isChecked() == true) {
                text2.visibility = android.view.View.VISIBLE
                rGroup1.visibility = android.view.View.VISIBLE
                imgOs.visibility = android.view.View.VISIBLE
            } else {
                text2.visibility = android.view.View.INVISIBLE
                rGroup1.visibility = android.view.View.INVISIBLE
                imgOs.visibility = android.view.View.INVISIBLE
            }
        }

        rGroup1.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.RdoOreo -> imgOs.setImageResource(R.drawable.oreo)
                R.id.RdoPie -> imgOs.setImageResource(R.drawable.andpie)
                R.id.RdoQ -> imgOs.setImageResource(R.drawable.andq)
            }
        }

        btnEnd.setOnClickListener {
            finish()
        }

        btnRst.setOnClickListener {
            swtAgree.isChecked = false
            rGroup1.clearCheck()
            imgOs.setImageResource(0)
        }

    }
}