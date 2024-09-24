package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class record_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_record)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_events -> {
                    // Переход на Main_activity
                    navigateToActivity(MainActivity::class.java)
                }

                R.id.nav_tickets -> {
                // Переход на ticket_acrivity
                navigateToActivity(ticket_activity::class.java)
                }

                R.id.nav_records -> {
                    // Здесь останемся в текущей активности
                }

            }
            true
        }
    }
        private fun navigateToActivity(activityClass: Class<*>) {
            val intent = Intent(this, activityClass)
            startActivity(intent)
        }



    }
