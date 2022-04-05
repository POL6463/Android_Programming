package com.example.project7_1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    lateinit  var baseLayout: LinearLayout
    lateinit  var button1: Button
    lateinit  var button2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "배경색 바꾸기(컨텍스트 메뉴)"
        baseLayout = findViewById<LinearLayout>(R.id.baseLayout) as LinearLayout
        button1 = findViewById<Button>(R.id.button1) as Button
        registerForContextMenu(button1)
        button2 = findViewById<Button>(R.id.button2) as Button
        registerForContextMenu(button2)
    }

    override fun onCreateContextMenu(menu: ContextMenu?,v: View?,menuInfo: ContextMenu.ContextMenuInfo? ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        var mInflater = this.menuInflater
        if (v === button1) {
            menu!!.setHeaderTitle("배경색 변경")
            mInflater.inflate(R.menu.menu1, menu)
        }
        if (v === button2) {
            mInflater.inflate(R.menu.menu2, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemRed -> {
                baseLayout.setBackgroundColor(Color.RED)
                return true
            }
            R.id.itemGreen -> {
                baseLayout.setBackgroundColor(Color.GREEN)
                return true
            }
            R.id.itemBlue -> {
                baseLayout.setBackgroundColor(Color.BLUE)
                return true
            }
            R.id.subRotate -> {
                button2.rotation = 45f
                return true
            }
            R.id.subSize -> {
                button2.scaleX = 2f
                return true
            }
        }
        return false
    }
}
