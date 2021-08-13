package com.example.storage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storage.databinding.ActivityScopedStorageBinding

class ScopedStorageActivity : AppCompatActivity() {

    private lateinit var uiBinding: ActivityScopedStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityScopedStorageBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ScopedStorageActivity::class.java)
        }
    }
}