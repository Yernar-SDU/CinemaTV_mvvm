package com.example.cinematv_mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.cinematv_mvvm.databinding.ActivityMainBinding
import com.example.cinematv_mvvm.utils.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentNavController: LiveData<NavController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

    }


    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.home_tab,
            R.navigation.search_tab,
            R.navigation.favorites_tab,
            R.navigation.profile_tab
        )
        val controller = binding.navView.setupWithNavController(
            navGraphIds=  navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        controller.observe(this) {
            currentNavController.value?.addOnDestinationChangedListener(listener)
        }
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController.value?.navigateUp() ?: false
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }


    private val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.profile_tab -> {
//                    binding.navView
                }
            }
        }
}