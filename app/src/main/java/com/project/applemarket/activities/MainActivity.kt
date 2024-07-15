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
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.applemarket.PostAdapter
import com.project.applemarket.R
import com.project.applemarket.data.MyData
import com.project.applemarket.data.Permission
import com.project.applemarket.data.Post
import com.project.applemarket.data.Sample
import com.project.applemarket.databinding.ActivityMainBinding
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var notificationManager: NotificationManager
    private val channelId = "0"

    private val postList = Sample.postList.toMutableList()

    private val backPressedCallback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showBackButtonDialog()
        }
    }
    private val recyclerView: RecyclerView by lazy {
        binding.postRv
    }
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this@MainActivity)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val activityResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data

            val post = data!!.getParcelableExtra("POST", Post::class.java)
            val isChanged = data.getBooleanExtra("IsChanged", false)
            val index = data.getIntExtra("INDEX", -1)
            Log.d("MainActivity", "isChanged: $isChanged")
            if (isChanged) {
                postList[index] = post!!
                Log.d("MainActivity", "index: $index")
                binding.postRv.adapter!!.notifyItemChanged(index)
            }
        }
    }

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

        binding.floatingButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(0)
        }

        with(recyclerView) {
            adapter = PostAdapter(postList).apply {
                setOnClickListener(object : PostAdapter.ClickListener {
                    override fun onPostClick(position: Int) {
                        Log.d("PostAdapter", "position: $position")
                        val toDetailActivity = Intent(this@MainActivity, DetailActivity::class.java).apply {
                            putExtra("POST", postList[position])
                            putExtra("INDEX", position)
                        }

                        activityResultLauncher.launch(toDetailActivity)
                    }
                    override fun onHeartClick(isSelected: Boolean, position: Int) {
                        Log.d("PostAdapter", "on Heart Click...")
                        if(isSelected) {
                            postList[position].interest++
                            MyData.interestsId.add(postList[position].id)
                        } else {
                            postList[position].interest--
                            MyData.interestsId.remove(postList[position].id)
                        }
                    }
                    override fun onChatClick() {
                        Log.d("PostAdapter", "on Chat Click...")
                    }

                    override fun onPostLongClick(position: Int) {
                        showDeletePostDialog(position)
                    }

                })
            }
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val firstVisibleItem: Int = linearLayoutManager.findFirstCompletelyVisibleItemPosition()

                    Log.d("MainActivity", "position: $firstVisibleItem")

                    if (firstVisibleItem == 0) {
                        binding.floatingButton.visibility = View.INVISIBLE
                    } else {
                        binding.floatingButton.visibility = View.VISIBLE
                    }
                }
            })
        }


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
    private fun showDeletePostDialog(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder
            .setMessage("상품 삭제")
            .setTitle("상품을 정말로 삭제하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
                println("position: $position")
                postList.removeAt(position)
                with(binding.postRv.adapter!!) {
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, postList.size)
                }


                postList.forEachIndexed { index, it ->
                    println("$index: $it")
                }
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