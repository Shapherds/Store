package com.example.storage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storage.databinding.ActivityWorkWithFileBinding

class WorkWithFileActivity : AppCompatActivity() {

    private lateinit var uiBinding: ActivityWorkWithFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityWorkWithFileBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, WorkWithFileActivity::class.java)
        }
    }
}