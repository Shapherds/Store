package com.example.storage

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

object ListCallback : DiffUtil.ItemCallback<Uri>() {

    override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
        return oldItem.toString() == newItem.toString()
    }
}