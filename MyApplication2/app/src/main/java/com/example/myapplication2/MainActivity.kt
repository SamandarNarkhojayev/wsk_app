package com.example.myapplication2

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private var events: List<Event> = listOf()
    private var filteredEvents: List<Event> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        // Инициализация BottomNavigationView
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_events -> {
                    // Оставляемся на текущей активности
                }

                R.id.nav_tickets -> {
                    // Переход на TicketsActivity
                    navigateToActivity(ticket_activity::class.java)
                }

                R.id.nav_records -> {
                    // Переход на RecordsActivity
                    navigateToActivity(record_activity::class.java)
                }
            }
            true
        }

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.event_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Загружаем события из JSON
        events = loadEvents(this)
        filteredEvents = events // Изначально показываем все события

        // Устанавливаем адаптер
        eventAdapter = EventAdapter(filteredEvents) { event ->
            // Устанавливаем статус на "Прочитано"
            event.status = "Прочитано"
            saveStatus(event)

            // Перемешиваем события
            updateEventList()
        }
        recyclerView.adapter = eventAdapter

        // Обработка нажатий на кнопки фильтров
        findViewById<Button>(R.id.filter_all).setOnClickListener {
            updateEventList(events) // Показать все события
        }

        findViewById<Button>(R.id.filter_unread).setOnClickListener {
            val unreadEvents = events.filter { it.status == "Непрочитано" }
            updateEventList(unreadEvents) // Показать только непрочитанные
        }

        findViewById<Button>(R.id.filter_read).setOnClickListener {
            val readEvents = events.filter { it.status == "Прочитано" }
            updateEventList(readEvents) // Показать только прочитанные
        }
    }

    // Функция для перехода на другую активность
    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private fun updateEventList(newList: List<Event> = events) {
        // Перемешиваем события так, чтобы прочитанные были первыми
        val sortedEvents = newList.sortedBy { it.status == "Непрочитано" }
        eventAdapter.updateEvents(sortedEvents) // Обновляем адаптер
    }

    private fun loadEvents(context: Context): List<Event> {
        return try {
            val inputStream = context.assets.open("events_data.json")
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<List<Event>>() {}.type
            Gson().fromJson<List<Event>>(reader, type) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    private fun saveStatus(event: Event) {
        val sharedPref = getSharedPreferences("event_status", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(event.title, event.status)
            apply()
        }
    }
}

