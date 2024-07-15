package com.project.applemarket.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.project.applemarket.R
import com.project.applemarket.data.MyData
import com.project.applemarket.data.Post
import com.project.applemarket.databinding.ActivityDetailBinding
import com.project.applemarket.databinding.ActivityMainBinding
import kotlin.reflect.KClass

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
         ActivityDetailBinding.inflate(layoutInflater)
    }
    private val post: Post by lazy {
        intent.getParcelableExtra("POST", Post::class.java)!!
    }
    private val index: Int by lazy {
        intent.getIntExtra("INDEX", -1).also {
            if (it == -1) throw Exception("Index was not put")
        }
    }
    private var isChanged = false

    private val backPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val intent = Intent(this@DetailActivity, MainActivity::class.java).apply {
                //TODO: Change tag to Resource of string.xml
                putExtra("POST", post)
                putExtra("INDEX", index)
                putExtra("IsChanged", isChanged)
            }

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onBackPressedDispatcher.addCallback(backPressedCallback)



        with(binding) {
            itemImage.setImageResource(post.item.image)
            nicknameText.text = post.user.nickname
            locationText.text = post.user.location
            thermometerText.text = "${post.user.manner}°C"

            titleText.text = post.title
            bodyText.text = post.body

            if(MyData.interests.contains(post)) {
                heartButton.isSelected = true
            }
            priceText.text = post.price.toString().reversed().mapIndexed { index, it ->
                if(index % 3 == 2 && index != post.price.toString().lastIndex) "${it},"
                else it
            }.joinToString("").reversed() + "원"

            heartButton.setOnClickListener {
                isChanged = !isChanged
                it.isSelected = !it.isSelected

                if(it.isSelected) {
                    MyData.interests.add(post)
                    post.interest++
                    Snackbar.make(heartButton, "관심 목록에 추가되었습니다.", Snackbar.LENGTH_SHORT).show()
                } else {
                    MyData.interests.remove(post)
                    post.interest--
                }
            }
            chatButton.setOnClickListener {
                Log.d("DetailActivity", "On Chat Button Clicked...")
            }
            backButton.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

    }


}