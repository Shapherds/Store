package com.example.storage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storage.databinding.ActivityFileWithoutPermissionBinding
import java.io.File

class FileWithoutPermissionActivity : AppCompatActivity() {

    private lateinit var uiBinding: ActivityFileWithoutPermissionBinding
    private val fileName = "Storage.txt"
    private var isInternalStorage: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityFileWithoutPermissionBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        createsFiles()
        uiBinding.externalStorageButton.setOnClickListener { loadExternalFile() }
        uiBinding.internalStorageButton.setOnClickListener { loadInternalFile() }
        uiBinding.saveFileButton.setOnClickListener {
            if (isInternalStorage != null && isInternalStorage == true) {
                openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
                    it.write(uiBinding.fileContentShowerEditText.text.toString())
                    Toast.makeText(this,"File saved", Toast.LENGTH_LONG).show()
                }
            } else if (isInternalStorage != null && isInternalStorage == false && isExternalStorageWritable()) {
                val file = File(getExternalFilesDir(null), fileName)
                file.outputStream()
                    .write(uiBinding.fileContentShowerEditText.text.toString().toByteArray())
                Toast.makeText(this,"File saved", Toast.LENGTH_LONG).show()
            } else Toast.makeText(this, "Storage is not available ", Toast.LENGTH_LONG).show()
        }
    }

    private fun createsFiles() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(IS_FILE_CREATED, true)) {
            openFileOutput(fileName, Context.MODE_PRIVATE).bufferedWriter().use {
                it.write("HI")
            }
            File(getExternalFilesDir(null), fileName).writeText("HI")
            with(sharedPref.edit()) {
                putBoolean(IS_FILE_CREATED, false)
                apply()
            }
        }
    }

    private fun loadInternalFile() {
        isInternalStorage = true
        openFileInput(fileName).bufferedReader().useLines { lines ->
            uiBinding.fileContentShowerEditText.setText(lines.fold("") { some, text ->
                text
            })
        }
    }

    private fun loadExternalFile() {
        if (isExternalStorageReadable()) {
            isInternalStorage = false
            val file = File(getExternalFilesDir(null), fileName)
            uiBinding.fileContentShowerEditText.setText(file.readText())
        } else Toast.makeText(this, "External storage is not available ", Toast.LENGTH_LONG).show()
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    companion object {

        private const val IS_FILE_CREATED = "file key"

        fun newIntent(context: Context): Intent {
            return Intent(context, FileWithoutPermissionActivity::class.java)
        }
    }
}