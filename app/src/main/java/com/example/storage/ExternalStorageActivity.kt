package com.example.storage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.storage.databinding.ActivityExternalStorageBinding

class ExternalStorageActivity : AppCompatActivity() {

    private lateinit var uiBinding: ActivityExternalStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        uiBinding = ActivityExternalStorageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(uiBinding.root)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ExternalStorageActivity::class.java)
        }
    }
}