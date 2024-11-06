package com.example.appcafecol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(
    private val notifications: List<Notification>,
    private val onNotificationClick: (Notification) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.notificationMessageTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.notificationTimestampTextView)

        fun bind(notification: Notification, onNotificationClick: (Notification) -> Unit) {
            messageTextView.text = notification.message
            timestampTextView.text = formatTimestamp(notification.timestamp)
            itemView.setOnClickListener { onNotificationClick(notification) }
        }

        private fun formatTimestamp(timestamp: Long): String {
            val currentTime = System.currentTimeMillis()
            val diff = currentTime - timestamp
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            return when {
                days > 0 -> "$days días atrás"
                hours > 0 -> "$hours horas atrás"
                minutes > 0 -> "$minutes minutos atrás"
                else -> "Justo ahora"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position], onNotificationClick)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }
}


