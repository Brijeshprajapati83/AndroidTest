package com.example.practicalexam

import android.R.attr
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.practicalexam.api.DashboardUtility
import com.example.practicalexam.api.UserService
import com.example.practicalexam.dashboardclass.DashboardRequest
import com.example.practicalexam.dashboardclass.DashboardResponse
import com.example.practicalexam.databinding.ActivityProfileDetailsBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileDetails : AppCompatActivity() {

    lateinit var binding: ActivityProfileDetailsBinding
    lateinit var sharedPreference: SharedPreferences
    private val pickImage = 100
    val REQUEST_CODE = 13
    private val FILE_NAME = "photo.jpg"
    private val CAMERA_REQUEST = 1888
    private var imageUri: Uri? = null
    val gson = Gson()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSave.setOnClickListener {
            val intent = Intent(this@ProfileDetails, UpdateActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        val dashboardDetail = gson.fromJson<DashboardResponse>(
            intent.getStringExtra("dashboard"),
            DashboardResponse::class.java
        )

        binding.etFirst.setText(dashboardDetail.data[0].your_name)
        Glide.with(this@ProfileDetails)
            .load(dashboardDetail.data[0].small_image_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.igProfile)



        binding.igProfile.setOnClickListener {

            val items = arrayOf("Gmail","Camera")
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Choose File")
            builder.setItems(items){ dialog, which ->
                Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
            }
            builder.setIcon(R.drawable.choose)


            builder.setNegativeButton("No"){dialogInterface, which ->
                Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }






//            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(gallery, pickImage)
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(cameraIntent, REQUEST_CODE)

//            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            val filePhoto = getPhotoFile(FILE_NAME)
//            val providerFile =
//                FileProvider.getUriForFile(this,"com.example.androidcamera.fileprovider", filePhoto)
//            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerFile)


        }
    }
//
//    private fun getPhotoFile(fileName: String): File {
//        val directoryStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(fileName, ".jpg", directoryStorage)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//


//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
//            val takenPhoto = BitmapFactory.decodeFile(getPhotoFile("photo.jpg").absolutePath)
//            binding.igProfile.setImageBitmap(takenPhoto)
//
//            imageUri = data?.data
//            binding.igProfile.setImageURI(imageUri)
//        }

//        if (resultCode === RESULT_OK && requestCode === pickImage
//            || requestCode === CAMERA_REQUEST
//        ) {
//            when (requestCode) {
//                pickImage -> {
//                    this.imageFromGallery(resultCode, attr.data,data)
//                    binding.igProfile.setImageBitmap(null)
////                    binding.igProfile.setImageBitmap(setphoto)
//                }
//                CAMERA_REQUEST -> {
//                    this.imageFromGallery(resultCode, attr.data,data)
//                    binding.igProfile.setImageBitmap(null)
////                    binding.igProfile.setImageBitmap(setphoto)
//                }
//                else -> {}
//            }
//        }

//    }

//    private fun imageFromGallery(resultCode: Int, requestCode: Int,data: Intent?) {
//                if (resultCode == RESULT_OK && requestCode == pickImage){
//            imageUri = data?.data
//            binding.igProfile.setImageURI(imageUri)
//        }
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray,
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == pickImage){
//            if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
//                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(cameraIntent, CAMERA_REQUEST)
//            } else {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
//            }
//        }
//    }

