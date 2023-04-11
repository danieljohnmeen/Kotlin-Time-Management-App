package com.example.timemanagementapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.timemanagementapp.UI.*

import com.example.timemanagementapp.databaseHandling.UserDatabase
import com.example.timemanagementapp.databaseHandling.UserDatabase.Companion.getInstance
import com.example.timemanagementapp.databaseHandling.timeDB.Time
import com.example.timemanagementapp.databaseHandling.timeDB.TimeDAO
import com.example.timemanagementapp.databaseHandling.timeDB.TimeViewModel
import com.example.timemanagementapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    var userDatabase: UserDatabase? = null
    var timeDAO: TimeDAO? = null
    var timeViewModel: TimeViewModel? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // will automatically bind all the views to its ids during runtime
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val drawerLayout = binding!!.drawerLayout
        val toolbar = binding!!.materialToolbar
        setSupportActionBar(toolbar)
        // to make this work, change in manifest.xml file the android:theme to no actionbar

        // add navigation icon in xml file to use this
        toolbar.setNavigationOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        val bottomNavigationView = binding!!.bottomNavBar
        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.stopwatch -> {
                    fragment = StopWatchFragment()
                    fragmentTransaction(fragment)
                    Toast.makeText(this@MainActivity, "stopwatch", Toast.LENGTH_SHORT).show()
                }
                R.id.home -> {
                    fragment = HomePageFragment()
                    fragmentTransaction(fragment)
                    Toast.makeText(this@MainActivity, "inserted", Toast.LENGTH_SHORT).show()
//                    insertTime()
                }
                R.id.report -> {
                    Toast.makeText(this@MainActivity, "selected", Toast.LENGTH_SHORT).show()
                    displayTime()
                }
            }
            false
        }
        userDatabase = getInstance(this@MainActivity)
        timeDAO = userDatabase!!.timeDao()


        val sideNavigation = binding!!.sideNavigationBar
        sideNavigation.setNavigationItemSelectedListener { item ->
            var fragment: Fragment = TodoListFragment()
            // setting this as a default fragment, the value will change inside of the when statement
            when(item.itemId)
            {
                R.id.todoTab ->             { fragment = TodoListFragment() }
                R.id.habitTrackerTab ->     { fragment = HabitFragment() }
                R.id.exerciseTab ->         { fragment = ExerciseFragment() }
            }

            fragmentTransaction(fragment)


            true
        }


    }

    @SuppressLint("SetTextI18n")
    fun insertTime(){
        timeViewModel = TimeViewModel(timeDAO!!)
        var count = 30
        val time: Time = Time(0, "11 $count am", "too much", "application work")
        timeViewModel?.insertTime(time)
        count += 1
    }


    @SuppressLint("SetTextI18n")
    fun displayTime(){
        val tvchange = binding!!.textView

        // you can add the select query in the view model but it requires a lot of unncessary code
        // so just implement it here it is more easier
        userDatabase?.timeDao()?.getTimeInfo()?.observe(this){ timeList ->
            for (element in timeList)
                tvchange.append(element.record_time + " ")

        }
    }

    fun fragmentTransaction(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
    }


}