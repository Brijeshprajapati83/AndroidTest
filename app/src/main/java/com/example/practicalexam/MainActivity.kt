package com.example.practicalexam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Patterns
import android.widget.Toast
import com.example.practicalexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var sharedPrefrence: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefrence = getSharedPreferences("SHARED_PREFRENCE", MODE_PRIVATE)!!

        if (sharedPrefrence.getBoolean("isLogin",false)){
            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            binding.btnLogin.setOnClickListener {
                if (binding.etEmailNumber.getText().toString().trim().isEmpty()) {
                    binding.etEmailNumber.error = "Username Required"
                    binding.etEmailNumber.requestFocus()

                } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmailNumber.text.toString().trim())
                        .matches() && !Patterns.PHONE.matcher(binding.etEmailNumber.text.toString().trim()).matches()
                ) {
                    binding.etEmailNumber.error = "Username Invalid"
                    binding.etEmailNumber.requestFocus()


                } else if (binding.etEmailNumber.length() == 10) {
                    binding.etEmailNumber.error = "Please enter 10 Digits..."
                    binding.etEmailNumber.requestFocus()


                }else if (binding.etPassword.text.toString().trim().isEmpty()) {
                    binding.etPassword.error = "Password Required"
                    binding.etPassword.requestFocus()

                } else if (binding.etPassword.length() < 6) {
                    binding.etPassword.error = "Password must be minimum 6 character Required"
                    binding.etPassword.requestFocus()

                } else {
                    val editor = sharedPrefrence.edit()
                    editor.putBoolean("isLogin",true)
                    editor.apply()
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}