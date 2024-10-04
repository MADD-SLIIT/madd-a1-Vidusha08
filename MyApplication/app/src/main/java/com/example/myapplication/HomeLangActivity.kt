package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityHomeLangBinding
import com.example.myapplication.util.UiUtil


class HomeLangActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeLangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeLangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Language overlay logic
        binding.worldIcon.setOnClickListener {
            closeLanguageOverlay()
        }

        binding.btnEnglish.setOnClickListener {
            // Switch to English and hide the overlay
            UiUtil.showToast(this, "English Selected")
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnSinhala.setOnClickListener {
            // Switch to HomeSiActivity
            UiUtil.showToast(this, "Sinhala Selected")
            // binding.languageOverlay.visibility = View.INVISIBLE // Hide overlay after selection
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

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

    private fun closeLanguageOverlay() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDetailsActivity() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }
}