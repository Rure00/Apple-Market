package com.project.applemarket

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.applemarket.data.MyData
import com.project.applemarket.data.Post
import com.project.applemarket.databinding.ItemLayoutBinding

class PostAdapter(private val postList: List<Post>): RecyclerView.Adapter<PostAdapter.Holder>() {

    private lateinit var clickListener: ClickListener

    inner class Holder(val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        val title = binding.titleText
        val location = binding.addressText
        val price = binding.priceText
        val image = binding.itemImage
        val heart = binding.heartButton
        val chat = binding.chatButton
    }

    fun setOnClickListener(listener: ClickListener) {
        this.clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder) {
            val post = postList[position]

            image.setImageResource(post.item.image)
            title.text = post.title
            location.text = post.user.location
            price.text = post.price.toString().reversed().mapIndexed { index, it ->
                if(index % 3 == 2 && index != post.price.toString().lastIndex) "${it},"
                else it
            }.joinToString("").reversed() + "원"

            with(heart) {
                text = post.interest.toString()
                if(MyData.interests.contains(postList[position])) {
                    isSelected = true
                }

                setOnClickListener {
                    it.isSelected = !it.isSelected
                    Log.d("PostAdapter", "isSelected: $isSelected")
                    clickListener.onHeartClick(it.isSelected, position)
                }
            }


            chat.text = post.chatNum.toString()
            chat.setOnClickListener {
                clickListener.onChatClick()
            }

            binding.root.setOnClickListener {
                clickListener.onPostClick(position)
            }
        }
    }

    override fun getItemCount(): Int = postList.size

    interface ClickListener {
        fun onPostClick(position: Int)
        fun onHeartClick(isSelected: Boolean, position: Int)
        fun onChatClick()
    }
}