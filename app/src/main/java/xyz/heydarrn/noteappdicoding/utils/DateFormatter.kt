package xyz.heydarrn.noteappdicoding.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("EEEEE/MMMM/yyyy HH:mm:ss",Locale("id", "ID") )
        val date = Date()
        return dateFormat.format(date)
    }
}