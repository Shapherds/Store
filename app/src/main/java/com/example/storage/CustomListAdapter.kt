package com.example.storage

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storage.databinding.RecycleItemBinding

class CustomListAdapter : ListAdapter<Uri, CustomListAdapter.ViewHolder>(ListCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val uiBinding = RecycleItemBinding.inflate(layoutInflater)
        return ViewHolder(uiBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.currentUri = getItem(position)
        holder.bind()
    }

    class ViewHolder(private val uiBinding: RecycleItemBinding) :
        RecyclerView.ViewHolder(uiBinding.root) {

        lateinit var currentUri: Uri

        fun bind() {
            Glide.with(uiBinding.root.context)
                .load(currentUri)
                .into(uiBinding.mainImageView)
        }
    }
}
