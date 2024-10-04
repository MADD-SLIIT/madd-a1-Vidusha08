package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityHomeSinBinding
import com.example.myapplication.util.UiUtil
import androidx.core.content.ContextCompat

class HomeSinActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeSinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeSinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Language overlay
        binding.worldIcon.setOnClickListener {
            showLanguageOverlay()
        }

        binding.btnSinhala.setOnClickListener {
            // Switch to Sinhala
            UiUtil.showToast(this, "Sinhala Selected")
            binding.languageOverlay.visibility = View.INVISIBLE // Hide overlay
        }

        binding.btnEnglish.setOnClickListener {
            // Switch to English
            UiUtil.showToast(this,"English Selected" )
            binding.languageOverlay.visibility = View.INVISIBLE // Hide overlay
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // Hide overlay when clicked outside
        binding.main.setOnTouchListener { _, _ ->
            if (binding.languageOverlay.visibility == View.VISIBLE) {
                binding.languageOverlay.visibility = View.INVISIBLE
            }
            false // Return false to allow further handling
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

        binding.bottomNavigation.menu.getItem(0).isChecked = false

        // Set selected item
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            // Reset all icon default color
            resetBottomNavigationIcons()

            when(menuItem.itemId){
                R.id.bottom_menu_home->{
                    UiUtil.showToast(this, "Home")
                    binding.bottomNavigation.menu.findItem(R.id.bottom_menu_home)
                        .icon?.setTint(ContextCompat.getColor(this, R.color.my_primary3))
                }
                R.id.bottom_menu_map->{
                    UiUtil.showToast(this, "Map")
                    binding.bottomNavigation.menu.findItem(R.id.bottom_menu_map)
                        .icon?.setTint(ContextCompat.getColor(this, R.color.my_primary3))
                }
                R.id.bottom_menu_bookmark->{
                    UiUtil.showToast(this, "Bookmark")
                    binding.bottomNavigation.menu.findItem(R.id.bottom_menu_map)
                        .icon?.setTint(ContextCompat.getColor(this, R.color.my_primary3))
                }
                R.id.bottom_menu_bell->{
                    UiUtil.showToast(this, "Notification")
                    binding.bottomNavigation.menu.findItem(R.id.bottom_menu_map)
                        .icon?.setTint(ContextCompat.getColor(this, R.color.my_primary3))
                }
                R.id.bottom_menu_profile->{
                    UiUtil.showToast(this, "Profile")
                    binding.bottomNavigation.menu.findItem(R.id.bottom_menu_map)
                        .icon?.setTint(ContextCompat.getColor(this, R.color.my_primary3))
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }
    }

    private fun showLanguageOverlay() {
        binding.languageOverlay.visibility = View.VISIBLE
    }

    private fun navigateToDetailsActivity() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    // Reset all icon colors to default
    private fun resetBottomNavigationIcons() {
        val defaultColor = ContextCompat.getColor(this, R.color.my_primary2)

        // Set all icons back to the default color
        binding.bottomNavigation.menu.findItem(R.id.bottom_menu_home)
            .icon?.setTint(defaultColor)
        binding.bottomNavigation.menu.findItem(R.id.bottom_menu_map)
            .icon?.setTint(defaultColor)
        binding.bottomNavigation.menu.findItem(R.id.bottom_menu_bookmark)
            .icon?.setTint(defaultColor)
        binding.bottomNavigation.menu.findItem(R.id.bottom_menu_bell)
            .icon?.setTint(defaultColor)
        binding.bottomNavigation.menu.findItem(R.id.bottom_menu_profile)
            .icon?.setTint(defaultColor)
    }
}