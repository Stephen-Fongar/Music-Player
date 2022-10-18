package com.kdsf.mp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdsf.mp.databinding.ActivityQueBinding

class QueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.topColor)

        super.onCreate(savedInstanceState)

        binding = ActivityQueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtnQA.setOnClickListener{finish()}
    }
}