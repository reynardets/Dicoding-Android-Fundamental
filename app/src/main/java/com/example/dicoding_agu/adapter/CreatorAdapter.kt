package com.example.dicoding_agu.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dicoding_agu.R
import com.example.dicoding_agu.databinding.ItemCreatorBinding
import com.example.dicoding_agu.model.Creator
import com.example.dicoding_agu.ui.activity.DetailActivity

class CreatorAdapter : RecyclerView.Adapter<CreatorAdapter.ViewHolder>() {

    private val user = ArrayList<Creator>()

    fun setData(items: ArrayList<Creator>) {
        user.clear()
        user.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_creator, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(user[position])

    override fun getItemCount(): Int = user.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCreatorBinding.bind(itemView)
        fun bind(userItem: Creator) {
            binding.apply {
                crtrUname.text = userItem.username
                Glide.with(itemView.context)
                    .load(userItem.avatar)
                    .apply(RequestOptions())
                    .into(photo)
            }
            itemView.setOnClickListener {
                val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                intentDetail.putExtra(DetailActivity.EXTRA_CREATOR, userItem)
                itemView.context.startActivity(intentDetail)
            }
        }
    }
}