package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.util.UiUtil
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Setup Bottom Navigation
        binding.bottomNavigation.menu.getItem(0).isChecked = false

        // Set selected item
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            // Reset all icon default color
            resetBottomNavigationIcons()

            when(menuItem.itemId){
                R.id.bottom_menu_home->{
                    UiUtil.showToast(this, "Home")
                    binding.bottomNavigation.menu.findItem(R.id.bottom_menu_map)
                        .icon?.setTint(ContextCompat.getColor(this, R.color.my_primary3))
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                R.id.bottom_menu_map->{
                    UiUtil.showToast(this, "Map")
                    binding.bottomNavigation.menu.findItem(R.id.bottom_menu_map)
                        .icon?.setTint(ContextCompat.getColor(this, R.color.my_primary3))
                    val intent = Intent(this, MapActivity::class.java)
                    startActivity(intent)
                }
                R.id.bottom_menu_bookmark->{
                    UiUtil.showToast(this, "Bookmark")
                    binding.bottomNavigation.menu.findItem(R.id.bottom_menu_map)
                        .icon?.setTint(ContextCompat.getColor(this, R.color.my_primary3))
                    val intent = Intent(this, WishlistActivity::class.java)
                    startActivity(intent)
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

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
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