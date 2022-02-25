package com.kwanso.grocerylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kwanso.grocerylist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpBottomNavViews()
    }

   private fun setUpBottomNavViews()
    {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragNavHost) as NavHostFragment
         navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)

        // Setting Up ActionBar with Navigation Controller
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.home_grocery_list,
                R.id.all_list_fragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



}