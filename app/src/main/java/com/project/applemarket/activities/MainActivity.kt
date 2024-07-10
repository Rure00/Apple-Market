package com.project.applemarket.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.applemarket.PostAdapter
import com.project.applemarket.R
import com.project.applemarket.data.Sample
import com.project.applemarket.databinding.ActivityMainBinding
import java.util.Stack

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //TODO: using drop menu
        val regions = listOf("내배캠동", "스파르타동")
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, regions)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        val postList = Sample.postList
        with(binding.postRv) {
            adapter = PostAdapter(postList).apply {
                setOnClickListener(object : PostAdapter.ClickListener {
                    override fun onPostClick(position: Int) {
                        Log.d("PostAdapter", "on Post Click...")
                        val toDetailActivity = Intent(this@MainActivity, DetailActivity::class.java).apply {
                            putExtra("POST", postList[position])
                        }
                        startActivity(toDetailActivity)
                    }
                    override fun onHeartClick() {
                        Log.d("PostAdapter", "on Heart Click...")

                    }
                    override fun onChatClick() {
                        Log.d("PostAdapter", "on Chat Click...")
                    }

                })
            }
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))


        }


    }
}