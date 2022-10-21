package com.loskon.usercrud.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.loskon.usercrud.R
import com.loskon.usercrud.util.AppPreference

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) setNavigationGraph()
    }

    private fun setNavigationGraph() {
/*        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navGraph = navHostFragment.navController.navInflater.inflate(R.navigation.main_graph)

        navGraph.startDestinationId =
            if (viewModel.isLoggedIn) {
                R.id.loginFragment
            } else {
                R.id.loginFragment
            }

        navHostFragment.navController.graph = navGraph*/

/*        val navController = findNavController(R.id.main_fragment_container)
        val graph = navController.graph

        if (AppPreference.isUserAuthenticated(this)) {
            graph.setStartDestination(R.id.loginFragment)
        } else {
            graph.setStartDestination(R.id.loginFragment)
        }

        navController.graph = graph*/

        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment)
        val navGraph = navHostFragment.navController.navInflater.inflate(R.navigation.main_graph)

        if (AppPreference.isUserAuthenticated(this)) {
            navGraph.setStartDestination(R.id.userListFragment)
        } else {
            navGraph.setStartDestination(R.id.loginFragment)
        }

        navHostFragment.navController.graph = navGraph
    }
}