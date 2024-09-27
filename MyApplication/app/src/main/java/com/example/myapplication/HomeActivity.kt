package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.databinding.ActivitySplashBinding
import com.example.myapplication.util.UiUtil

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.placeImg1.setOnClickListener {
            navigateToDetailsActivity()
        }

        binding.placeName1.setOnClickListener {
            navigateToDetailsActivity()
        }

        binding.placeDesc1.setOnClickListener {
            navigateToDetailsActivity()
        }

        binding.placeImg2.setOnClickListener {
            navigateToDetailsActivity()
        }

        binding.placeName2.setOnClickListener {
            navigateToDetailsActivity()
        }

        binding.placeDesc2.setOnClickListener {
            navigateToDetailsActivity()
        }

        binding.bottomNavigation.setOnItemSelectedListener {menuItem->
            when(menuItem.itemId){
                R.id.bottom_menu_home->{
                    UiUtil.showToast(this, "Home")
                }
                R.id.bottom_menu_map->{
                    UiUtil.showToast(this, "Map")
                }
                R.id.bottom_menu_bookmark->{
                    UiUtil.showToast(this, "Bookmark")
                }
                R.id.bottom_menu_bell->{
                    UiUtil.showToast(this, "Notification")
                }
                R.id.bottom_menu_profile->{
                    UiUtil.showToast(this, "Profile")
                }

            }
            false
        }
    }
    private fun navigateToDetailsActivity() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }
}