package com.example.storage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.storage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var uiBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        uiBinding.photoInStorageButton.setOnClickListener {
            startActivity(
                ExternalStorageActivity.newIntent(this)
            )
        }
        uiBinding.savePhotoByCameraButton.setOnClickListener {
            startActivity(
                ScopedStorageActivity.newIntent(
                    this
                )
            )
        }
        uiBinding.withoutPermissionButton.setOnClickListener {
            startActivity(
                FileWithoutPermissionActivity.newIntent(this)
            )
        }
        uiBinding.workWithFileButton.setOnClickListener {
            startActivity(
                WorkWithFileActivity.newIntent(
                    this
                )
            )
        }
    }
}