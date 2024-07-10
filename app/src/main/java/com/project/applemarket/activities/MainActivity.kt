package com.project.applemarket.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.applemarket.PostAdapter
import com.project.applemarket.R
import com.project.applemarket.data.MyData
import com.project.applemarket.data.Sample
import com.project.applemarket.databinding.ActivityMainBinding
import java.util.Stack
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val backPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showBackButtonDialog()
        }
    }

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

        onBackPressedDispatcher.addCallback(this, backPressedCallback)

        //TODO: using drop menu
        val regions = listOf("내배캠동", "스파르타동")
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, regions)
        with(binding.autoCompleteTextView) {
            setText(regions[0])
            setAdapter(arrayAdapter)
        }

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
                    override fun onHeartClick(isSelected: Boolean, position: Int) {
                        Log.d("PostAdapter", "on Heart Click...")
                        if(isSelected) {
                            MyData.interests.add(postList[position])
                        } else {
                            MyData.interests.remove(postList[position])
                        }
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

    override fun onResume() {
        super.onResume()

    }

    private fun showBackButtonDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder
            .setMessage("종료")
            .setTitle("정말 종료하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
                finishAffinity()
                exitProcess(0)
            }
            .setNegativeButton("취소") { dialog, which ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}