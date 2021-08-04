package com.example.storage

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.storage.databinding.ActivityScopedStorageBinding
import java.io.OutputStream

class ScopedStorageActivity : AppCompatActivity() {

    private lateinit var uiBinding: ActivityScopedStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityScopedStorageBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        if (!isAppPermissionsAvailable()) {
            getPermissionRequest()
        }
        uiBinding.doPhotoButton.setOnClickListener {
            if (!isAppPermissionsAvailable()) {
                getPermissionRequest()
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, REQUEST_CODE)
            }
        }
    }

    private fun getPermissionRequest() {
        val list = mutableListOf<String>()
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            list += Manifest.permission.CAMERA
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                list += Manifest.permission.WRITE_EXTERNAL_STORAGE
                ActivityCompat.requestPermissions(
                    this,
                    list.toTypedArray(),
                    MULTI_PERMISSIONS
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val imageres: Bitmap = data?.extras?.get("data") as Bitmap
            saveMediaToStorage(imageres)
        }
    }

    private fun isAppPermissionsAvailable(): Boolean {
        return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        var fos: OutputStream?
        var imageUri: Uri?
        val contentResolver = application.contentResolver
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
        fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }
        Toast.makeText(this,"Image saved", Toast.LENGTH_LONG).show()
    }

    companion object {

        private const val REQUEST_CODE = 1888
        private const val MULTI_PERMISSIONS = 101

        fun newIntent(context: Context): Intent {
            return Intent(context, ScopedStorageActivity::class.java)
        }
    }
}