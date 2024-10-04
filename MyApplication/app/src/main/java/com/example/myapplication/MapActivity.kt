package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.example.myapplication.util.UiUtil

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var searchInput: EditText

    lateinit var binding : ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Google Maps API
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize the Places API
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)

        // Setup search functionality
        searchInput = findViewById(R.id.search_input)

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text has changed
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called to notify you that the text is about to change
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.isNotEmpty()) {
                    searchPlace(s.toString())  // Trigger the place search
                }
            }
        })

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
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
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

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Default location in Sri Lanka (Colombo)
        val defaultLocation = LatLng(6.9271, 79.8612)
        googleMap.addMarker(MarkerOptions().position(defaultLocation).title("Marker in Colombo"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
    }

    // Search for a place based on user input
    private fun searchPlace(query: String) {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .setCountries("LK") // Restrict search to Sri Lanka
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                for (prediction: AutocompletePrediction in response.autocompletePredictions) {
                    val placeId = prediction.placeId
                    val placeName = prediction.getPrimaryText(null).toString()

                    // Fetch place details based on the place ID
                    fetchPlaceDetails(placeId, placeName)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MapActivity", "Place not found: ${exception.message}")
            }
    }

    // Fetch place details by place ID and move the map
    private fun fetchPlaceDetails(placeId: String, placeName: String) {
        val request = com.google.android.libraries.places.api.net.FetchPlaceRequest.builder(
            placeId,
            listOf(com.google.android.libraries.places.api.model.Place.Field.LAT_LNG)
        ).build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                val latLng = place.latLng
                if (latLng != null) {
                    googleMap.addMarker(MarkerOptions().position(latLng).title(placeName))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MapActivity", "Place details not found: ${exception.message}")
            }
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
