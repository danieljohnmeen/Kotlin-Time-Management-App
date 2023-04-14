package com.example.timemanagementapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.timemanagementapp.databaseHandling.TimeFirebase
import com.example.timemanagementapp.ui.*
import com.example.timemanagementapp.databaseHandling.UserDatabase
import com.example.timemanagementapp.databaseHandling.UserDatabase.Companion.getInstance
import com.example.timemanagementapp.databaseHandling.timeDB.Time
import com.example.timemanagementapp.databaseHandling.timeDB.TimeDAO
import com.example.timemanagementapp.databaseHandling.timeDB.TimeViewModel
import com.example.timemanagementapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // will automatically bind all the views to its ids during runtime
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        materialToolBarFunctions()
        bottomNavBarFunctions()
        sideNavBarFunctions()

        firestore = FirebaseFirestore.getInstance()
//        val collection = firestore.collection("User Details")
//        collection.get().addOnSuccessListener { value ->
//
//            Log.d("Firestore Data", value.toObjects(TimeFirebase::class.java).toString())
//        }

    }

    private fun materialToolBarFunctions(){
        val drawerLayout = binding.drawerLayout
        val toolbar = binding.materialToolbar
        setSupportActionBar(toolbar)
        // to make this work, change in manifest.xml file the android:theme to no actionbar

        // add navigation icon in xml file to use this
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
    }

    private fun bottomNavBarFunctions(){
        val bottomNavigationView = binding.bottomNavBar
        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.stopwatch -> {
                    fragment = StopWatchFragment()
                    fragmentTransaction(fragment)
                    Toast.makeText(this, "stopwatch", Toast.LENGTH_SHORT).show()
                }
                R.id.home -> {
                    fragment = HomePageFragment()
                    fragmentTransaction(fragment)
                    Toast.makeText(this, "homepage", Toast.LENGTH_SHORT).show()
//                    insertTime()
                }
                R.id.report -> {
                    Toast.makeText(this, "report", Toast.LENGTH_SHORT).show()
                    fragment = ReportFragment()
                    fragmentTransaction(fragment)
                }
            }
            false
        }

    }

    private fun sideNavBarFunctions(){
        val sideNavigation = binding.sideNavigationBar
        sideNavigation.setNavigationItemSelectedListener { item ->
            lateinit var fragment: Fragment
            // setting this as a default fragment, the value will change inside of the when statement
            when(item.itemId)
            {
                R.id.todoTab ->             { fragment = TodoListFragment() }
                R.id.habitTrackerTab ->     { fragment = HabitFragment() }
                R.id.exerciseTab ->         { fragment = ExerciseFragment() }
            }

            fragmentTransaction(fragment)

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            false
        }
    }

    // database handling
//    private fun insertTime(){
//        timeViewModel = TimeViewModel(timeDAO)
//        var count = 30
//        val time = Time(0, "11 $count am", "too much", "application work")
//        timeViewModel.insertTime(time)
//        count += 1
//    }


    private fun fragmentTransaction(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
    }

    // handling tool bar options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_options, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
            }
            R.id.search -> Toast.makeText(this, "Search Selected", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


}