package com.alexeyp.users.utils

fun getTimeText(receivedTimeMillis: Long): String {
    val currentTime = System.currentTimeMillis()
    val differenceSeconds = (currentTime - receivedTimeMillis)/1000

    return when {
        differenceSeconds < 60 -> {
            "Created just now"
        }
        differenceSeconds < 3600 -> {
            val minutes = differenceSeconds / 60
            if (minutes.toInt() == 1) {
                "Created $minutes minute ago"
            } else {
                "Created $minutes minutes ago"
            }
        }
        differenceSeconds < 86400 -> {
            val hours = differenceSeconds / 3600
            if (hours.toInt() == 1) {
                "Created $hours hour ago"
            } else {
                "Created $hours hours ago"
            }
        }
        else -> {
            val days = differenceSeconds / 86400
            if (days.toInt() == 1) {
                "Created $days day ago"
            } else {
                "Created $days days ago"
            }
        }
    }
}