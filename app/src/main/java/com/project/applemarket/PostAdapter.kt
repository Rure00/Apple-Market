package com.project.applemarket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.applemarket.data.Post
import com.project.applemarket.databinding.ItemLayoutBinding

class PostAdapter(private val postList: List<Post>): RecyclerView.Adapter<PostAdapter.Holder>() {
    inner class Holder(binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.titleText
        val location = binding.addressText
        val price = binding.priceText
        val image = binding.itemImage
        val heart = binding.heartButton
        val chat = binding.chatButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder) {
            val post = postList[position]

            image.setImageResource(post.item.image)
            title.text = post.title
            location.text = post.user.location
            price.text = post.price.toString().reversed().mapIndexed { index, it ->
                if(index % 3 == 2 && index != post.price.toString().lastIndex) "${it},"
                else it
            }.joinToString("").reversed()

            heart.text = post.interest.toString()
            chat.text = post.chatNum.toString()
        }
    }

    override fun getItemCount(): Int = postList.size
}