package com.example.appcafecol

class Utils {
    fun getTimeAgo(timestamp: Long): String {
        // Implementación para calcular el tiempo transcurrido
        // Este es solo un ejemplo básico
        val currentTime = System.currentTimeMillis()
        val diff = currentTime - timestamp

        return when {
            diff < 60000 -> "Hace unos segundos"
            diff < 3600000 -> "${diff / 60000} minutos atrás"
            diff < 86400000 -> "${diff / 3600000} horas atrás"
            else -> "${diff / 86400000} días atrás"
        }
    }
}
