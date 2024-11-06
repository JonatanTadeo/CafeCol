package com.example.appcafecol

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class NotificationDetailsDialogFragment : DialogFragment() {

    private lateinit var notification: Notification

    companion object {
        private const val ARG_NOTIFICATION = "notification"

        fun newInstance(notification: Notification): NotificationDetailsDialogFragment {
            val fragment = NotificationDetailsDialogFragment()
            val args = Bundle()
            args.putParcelable(ARG_NOTIFICATION, notification)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            notification = it.getParcelable(ARG_NOTIFICATION)!!
            Log.d("Notification", "Notificación recibida en diálogo: ${notification.message}, ${notification.userEmail}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification_details_dialog, container, false)

        view.findViewById<TextView>(R.id.notificationMessageTextView).text = notification.message
        view.findViewById<TextView>(R.id.notificationTimestampTextView).text = formatTimestamp(notification.timestamp)
        view.findViewById<TextView>(R.id.applicantEmailTextView).text = notification.userEmail

        return view
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

