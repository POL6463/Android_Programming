package com.example.ch05_06

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {
    lateinit var button1 : Button
    lateinit var image1 : ImageView
    lateinit var image2 : ImageView
    lateinit var image3 : ImageView

    var count = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        title="2018110006 김민웅"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1 = findViewById<Button>(R.id.Button1)
        image1 = findViewById<ImageView>(R.id.Image1)
        image2 = findViewById<ImageView>(R.id.Image2)
        image3 = findViewById<ImageView>(R.id.Image3)



        button1.setOnClickListener {
            count++

            if(count % 3 == 0) {
                image1.visibility = View.VISIBLE
                image2.visibility = View.INVISIBLE
                image3.visibility = View.INVISIBLE
            }
            else if(count % 3 == 1) {
                image1.visibility = View.INVISIBLE
                image2.visibility = View.VISIBLE
                image3.visibility = View.INVISIBLE
            }
            else {
                image1.visibility = View.INVISIBLE
                image2.visibility = View.INVISIBLE
                image3.visibility = View.VISIBLE
            }
        }

    }
}