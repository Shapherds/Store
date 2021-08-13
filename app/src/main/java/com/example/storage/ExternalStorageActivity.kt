package com.example.storage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.storage.databinding.ActivityExternalStorageBinding

class ExternalStorageActivity : AppCompatActivity() {

    private lateinit var uiBinding: ActivityExternalStorageBinding
    private lateinit var customListAdapter: CustomListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        uiBinding = ActivityExternalStorageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(uiBinding.root)
        customListAdapter = CustomListAdapter()
        uiBinding.recyclerView.adapter = customListAdapter
        getPhotos()
    }

    private fun getPhotos() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURES_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val resultList = mutableListOf<Uri>()
        if (requestCode == SELECT_PICTURES_CODE) {
            if (resultCode == RESULT_OK) {
                if (data?.clipData != null) {
                    for (i in 0 until data.clipData!!.itemCount) {
                        val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                        resultList.add(imageUri)
                    }
                } else if (data?.data != null) {
                    val imagePath = data.data!!
                    resultList.add(imagePath)
                }
                customListAdapter.submitList(resultList)
            }
        }
    }

    companion object {

        private const val SELECT_PICTURES_CODE = 101

        fun newIntent(context: Context): Intent {
            return Intent(context, ExternalStorageActivity::class.java)
        }
    }
}