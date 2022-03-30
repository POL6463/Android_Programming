package com.example.project8_1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var dp : DatePicker
    lateinit var edtDiary : EditText
    lateinit var btnWrite : Button
    lateinit var fileName : String

    var strSDpath = Environment.getExternalStorageDirectory().absolutePath
    var myDir = File("$strSDpath/myDir")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "간단 일기장"


        myDir.mkdirs()

        dp = findViewById<DatePicker>(R.id.datePicker1)
        edtDiary = findViewById<EditText>(R.id.edtDiary)
        btnWrite = findViewById<Button>(R.id.btnWrite)

        var cal = Calendar.getInstance()
        var cYear = cal.get(Calendar.YEAR)
        var cMonth = cal.get(Calendar.MONTH)
        var cDay = cal.get(Calendar.DAY_OF_MONTH)
        fileName = (Integer.toString(cYear) + "_"
                + Integer.toString(cMonth + 1) + "_"
                + Integer.toString(cDay) + ".txt")
        var str = readDiary(fileName)
        edtDiary.setText(str)
        btnWrite.isEnabled = true

        dp.init(cYear, cMonth, cDay) { view, year, monthOfYear, dayOfMonth ->
            fileName = (Integer.toString(year) + "_"
                    + Integer.toString(monthOfYear + 1) + "_"
                    + Integer.toString(dayOfMonth) + ".txt")
            var str = readDiary(fileName)
            edtDiary.setText(str)
            btnWrite.isEnabled = true
        }

        btnWrite.setOnClickListener {
            var outFs = File("$strSDpath/myDir/${fileName}").outputStream()
            var str = edtDiary.text.toString()
            outFs.write(str.toByteArray())
            outFs.close()
            Toast.makeText(applicationContext, "$fileName 이 저장됨", Toast.LENGTH_SHORT).show()
        }
    }

    fun readDiary(fName: String) : String? {
        var diaryStr : String? = null
        var fileDir = File("$strSDpath/myDir/$fName")
        var inFs : FileInputStream
        try {
            inFs = fileDir.inputStream()
            var txt = ByteArray(500)
            inFs.read(txt)
            inFs.close()
            diaryStr = txt.toString(Charsets.UTF_8).trim()
            btnWrite.text = "수정 하기"
        } catch (e : IOException) {
            edtDiary.hint = "일기 없음"
            btnWrite.text = "새로 저장"
        }
        return diaryStr
    }
}