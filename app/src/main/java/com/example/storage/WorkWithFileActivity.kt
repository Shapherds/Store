package com.example.storage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storage.databinding.ActivityWorkWithFileBinding
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class WorkWithFileActivity : AppCompatActivity() {

    private lateinit var uiBinding: ActivityWorkWithFileBinding
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityWorkWithFileBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        uiBinding.choiceButton.setOnClickListener {
            getFile()
        }
        uiBinding.saveFileButton.setOnClickListener {
            val writer =
                BufferedWriter(OutputStreamWriter(filePath?.let {
                    contentResolver.openOutputStream(
                        it
                    )
                }))
            writer.write(uiBinding.fileContentShower.text.toString())
            writer.close()
            Toast.makeText(this,"File saved", Toast.LENGTH_LONG).show()
        }
    }

    private fun getFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).setType("text/plain")
        startActivityForResult(intent, 7)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        filePath = data?.data
        val reader =
            BufferedReader(InputStreamReader(filePath?.let { contentResolver.openInputStream(it) }))
        uiBinding.fileContentShower.setText(reader.readText())
        reader.close()
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, WorkWithFileActivity::class.java)
        }
    }
}