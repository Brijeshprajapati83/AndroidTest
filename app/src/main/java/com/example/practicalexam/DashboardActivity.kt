package com.example.practicalexam

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.practicalexam.adapter.ViewPagerAdapter
import com.example.practicalexam.api.DashboardUtility
import com.example.practicalexam.api.UserService
import com.example.practicalexam.dashboardclass.DashboardRequest
import com.example.practicalexam.dashboardclass.DashboardResponse
import com.example.practicalexam.dashboardclass.DataDash
import com.example.practicalexam.dashboardclass.HeaderClass
import com.example.practicalexam.databinding.ActivityDashboardBinding
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityDashboardBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var preferences: SharedPreferences
    lateinit var sharedPref : SharedPreferences
    var position = 0

    @SuppressLint("CheckResult", "CommitPrefEdits", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val header = binding.navView.getHeaderView(0)
        val username = header.findViewById<TextView>(R.id.tvName)
        val userphone = header.findViewById<TextView>(R.id.tvMobileNumber)
        val userimg = header.findViewById<ImageView>(R.id.ivProfile)

        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)!!


        val list: MutableList<String> = ArrayList()

        list.add("https://picsum.photos/seed/picsum/200/300")
        list.add("https://cdn.maikoapp.com/3d4b/4r2dg/180h.jpg")
        list.add("https://cdn.maikoapp.com/3d4b/4qgko/f200.jpg")

        val viewPagerAdapter = ViewPagerAdapter(this@DashboardActivity, list)
        binding.viewPager.adapter = viewPagerAdapter


        binding.tabHomeIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                position = tab!!.position
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.tabHomeIndicator.setupWithViewPager(binding.viewPager)


        binding.apply {
            toggle =
                ActionBarDrawerToggle(this@DashboardActivity,
                    drawerLayout,
                    R.string.open,
                    R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding.btnLogout.setOnClickListener {

                val preferences = getSharedPreferences("SHARED_PREFRENCE", MODE_PRIVATE)
                val editor = preferences.edit()
                editor.clear()
                editor.apply()
                finish()
                val intent = Intent(this@DashboardActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
            }

            binding.navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.itemList -> {
                        val intent = Intent(this@DashboardActivity, ProfileActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@DashboardActivity, "success", Toast.LENGTH_SHORT).show()

                    }
                }
                true
            }
        }
        var dashboardResponse: DashboardResponse
        var connected = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        connected = networkInfo != null && networkInfo.isConnected

        if (connected) {

            val header = HashMap<String, String>()
            header["username"] = "13266491439396873377005327553733912388280651873617"
            var dashboardRequest = DashboardRequest(
                entity_type_id = "4",
                device_type = "2",
                user_id = "168"

            )

            val dashboardApi: UserService =
                DashboardUtility.getDashboardUser().create(UserService::class.java)

            val callDashboard: Call<DashboardResponse> =
                dashboardApi.sendDashboardData(header, dashboardRequest)

            callDashboard.enqueue(object : Callback<DashboardResponse> {
                override fun onResponse(
                    call: Call<DashboardResponse>,
                    response: Response<DashboardResponse>,
                ) {
                    if (response.isSuccessful){

                       dashboardResponse  = response.body()!!

                        binding.firstname.text = dashboardResponse.data[position].your_name
                        binding.phoneNo.text = dashboardResponse.data[position].mobile1
                        binding.email.text = dashboardResponse.data[position].email
                        binding.contryName.text = dashboardResponse.data[position].city_name

                        username.text = dashboardResponse.data[position].your_name
                        userphone.text = dashboardResponse.data[position].mobile1

                        Glide.with(this@DashboardActivity)
                            .load(dashboardResponse.data[position].large_image_url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(userimg)

                        Glide.with(this@DashboardActivity)
                            .load(dashboardResponse.data[position].large_image_url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(binding.profileImage)

                        binding.btnEdit.setOnClickListener {
                            val gson = Gson()
                            val intent = Intent(this@DashboardActivity,ProfileDetails::class.java)
                            intent.putExtra("dashboard",gson.toJson(dashboardResponse))
                            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }


                    }else{
                        val hashMap: HashMap<Any?, Any?> = HashMap<Any?, Any?>()
                        val gson = Gson()
                        val errorData: ErrorResponseModel = gson.fromJson(
                            response.errorBody()!!.string(),
                            ErrorResponseModel::class.java
                        )
                    }
                }



                override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                    Toast.makeText(this@DashboardActivity, "error: " + t.message, Toast.LENGTH_SHORT)
                        .show()
                }


            })


            }




        }
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            if (toggle.onOptionsItemSelected(item)) {
                true
            }
            return super.onOptionsItemSelected(item)
        }

}