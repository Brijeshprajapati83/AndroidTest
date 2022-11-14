package com.example.practicalexam

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicalexam.adapter.ProfileAdapter
import com.example.practicalexam.api.ApiUtility
import com.example.practicalexam.api.UserService
import com.example.practicalexam.databinding.ActivityProfileBinding
import com.example.practicalexam.modelclass.UserData
import com.example.practicalexam.modelclass.UserRequest
import com.example.practicalexam.modelclass.UserResponse
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class ProfileActivity : AppCompatActivity(), ProfileAdapter.OnItemClickListener {

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var binding: ActivityProfileBinding
    var recycleViewAdapter: ProfileAdapter? = null
    private var profileList = arrayListOf<UserData>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycleView.layoutManager = LinearLayoutManager(this)


        var userResponse: UserResponse

        binding.recycleView.setHasFixedSize(true)

        binding.recycleView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recycleView.layoutManager = linearLayoutManager


        var connected = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        connected = networkInfo != null && networkInfo.isConnected

        if (connected) {

            val headers = HashMap<String, String>()
            headers["username"] = "97050742860911837609205297880513125633235443797732"
            val userRequest = UserRequest(
                device_type = "2",
                entity_type_id = "3",
                user_id = "91"

            )

            Log.e(TAG, "onCreate: " + userRequest)


            val userApi: UserService = ApiUtility.getUser().create(UserService::class.java)

            val call: Call<UserResponse> = userApi.sendUserData(headers, userRequest)


            call.enqueue(object : Callback<UserResponse> {

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>,
                ) {


                    if (response.isSuccessful) {


                        userResponse = response.body()!!

                        recycleViewAdapter = ProfileAdapter(this@ProfileActivity,
                            userResponse.data.user_data as ArrayList<UserData>,
                            this@ProfileActivity)

                        binding.recycleView.adapter = recycleViewAdapter

                    } else {
                        val hashMap: HashMap<Any?, Any?> = HashMap<Any?, Any?>()
                        val gson = Gson()
                        val errorData: ErrorResponseModel = gson.fromJson(
                            response.errorBody()!!.string(),
                            ErrorResponseModel::class.java
                        )
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@ProfileActivity, "error: " + t.message, Toast.LENGTH_SHORT)
                        .show()

                }
            })
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
        }
        binding.recycleView.adapter?.notifyDataSetChanged()
    }


    override fun onItemClick(userData: UserData, position: Int) {
        val newUserList: ArrayList<UserData> = profileList

        newUserList.remove(userData)
        val args = Bundle()
        args.putSerializable("ARRAYLIST", newUserList as Serializable)
        intent.putExtra("BUNDLE", args)

        Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val deletedCourse:  UserData =
                    newUserList[viewHolder.adapterPosition]

                val position = viewHolder.adapterPosition

                profileList.removeAt(viewHolder.adapterPosition)

                recycleViewAdapter?.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(binding.recycleView,"Deleted " + deletedCourse.country_name,Snackbar.LENGTH_LONG).setAction(
                    "Undo",View.OnClickListener {
                        profileList.add(position, deletedCourse)
                        recycleViewAdapter?.notifyItemInserted(position)
                    }).show()
            }
            }).attachToRecyclerView(binding.recycleView)
        }

}
