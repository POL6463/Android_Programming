package com.tukorea.project10_2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "액티비티 테스트 예제"
        android.util.Log.i("액티비티 테스트", "onCreate()")

        var btnDial = findViewById<Button>(R.id.btnDial)
        btnDial.setOnClickListener {
            var uri = Uri.parse("tel:010-1234-5678")
            var intent = Intent(Intent.ACTION_DIAL, uri)
            startActivity(intent)
        }

        var btnFinish = findViewById<Button>(R.id.btnFinish)
        btnFinish.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        android.util.Log.i("액티비티 테스트", "onDestroy()")
    }

    override fun onPause() {
        super.onPause()
        android.util.Log.i("액티비티 테스트", "onPause()")
    }

    override fun onRestart() {
        super.onRestart()
        android.util.Log.i("액티비티 테스트", "onRestart()")
    }

    override fun onResume() {
        super.onResume()
        android.util.Log.i("액티비티 테스트", "onResume()")
    }

    override fun onStart() {
        super.onStart()
        android.util.Log.i("액티비티 테스트", "onStart()")
    }

    override fun onStop() {
        super.onStop()
        android.util.Log.i("액티비티 테스트", "onStop()")
    }

}