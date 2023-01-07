package com.example.notesappfirebase.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.notesappfirebase.R
import com.example.notesappfirebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        //addTestDataToFireStore()



    }

    private fun addTestDataToFireStore() {


        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Martin"
        user["last"] = "Wainaina"
        user["born"] = 1999

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Adding data success", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Adding data success")
            }
            .addOnFailureListener{
                Toast.makeText(this, "Adding data failed\n" +
                        "Hint:${it.message}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Adding data failed. \nHint:${it.message}")
            }
    }
}