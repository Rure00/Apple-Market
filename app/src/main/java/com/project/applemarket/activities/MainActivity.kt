package com.project.applemarket.activities

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.applemarket.PostAdapter
import com.project.applemarket.R
import com.project.applemarket.data.MyData
import com.project.applemarket.data.Permission
import com.project.applemarket.data.Sample
import com.project.applemarket.databinding.ActivityMainBinding
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var notificationManager: NotificationManager
    private val channelId = "0"

    private val backPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showBackButtonDialog()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

        val mChannel = NotificationChannel(channelId, "test", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)

        onBackPressedDispatcher.addCallback(this, backPressedCallback)
        requestPermissions(Permission.notification, Permission.NOTIFICATION_CODE)


        val regions = listOf("내배캠동", "스파르타동")
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_item, regions)
        with(binding.autoCompleteTextView) {
            setText(regions[0])
            setAdapter(arrayAdapter)
        }

        binding.notificationButton.setOnClickListener {
            Log.d("MainActivity", "on Notification Button Clicked...")
            sendNotification()
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
        //TODO: add adapter.notifyDataSetChanged()
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
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification_img)
            .setContentTitle("알림")
            .setContentText("알림이 도착했습니다!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("MainActivity: Notification", "Request Permission is Allowed.")
            notificationManager.notify(channelId, 0, builder.build())
        } else {
            Log.d("MainActivity: Notification", "Request Permission is Denied.")
            Toast.makeText(applicationContext, "권한이 거부되어 있습니다.\n 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()

            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.setData(uri)
            startActivity(intent)
        }
    }
}