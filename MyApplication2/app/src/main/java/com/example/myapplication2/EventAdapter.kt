

package com.example.myapplication2

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(
    private var events: List<Event>,
    private val onDoubleClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.event_title)
        val description: TextView = view.findViewById(R.id.event_description)
        val image: ImageView = view.findViewById(R.id.event_image)
        val status: TextView = view.findViewById(R.id.event_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.title.text = event.title
        holder.description.text = event.description

        // Загрузка изображения
        val resId = holder.itemView.context.resources.getIdentifier(
            event.imageUrl,
            "drawable",
            holder.itemView.context.packageName
        )
        holder.image.setImageResource(if (resId != 0) resId else R.drawable.def_image)

        holder.status.text = event.status

        // Обработка кликов
        holder.itemView.setOnClickListener {
            // Установим задержку для двойного нажатия
            Handler().postDelayed({
                // Это одиночный клик, можно выполнить нужные действия
            }, 300) // Задержка 300 мс

            // Если двойное нажатие
            holder.itemView.setOnClickListener {
                onDoubleClick(event) // Вызываем лямбда-функцию при двойном нажатии
            }
        }
    }

    override fun getItemCount(): Int = events.size

    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}


