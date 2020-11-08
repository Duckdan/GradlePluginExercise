package com.study.androidgradlestudydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.net.Uri
import androidx.core.content.ContextCompat.getSystemService


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.setOnClickListener {
            val intent = Intent()
            intent.data = Uri.parse("opencasic://com.zxy.casic?phone=15811021056&companyId=zzz")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
