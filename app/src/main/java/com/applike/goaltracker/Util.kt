package com.applike.goaltracker

import android.app.AlertDialog
import android.content.DialogInterface
import java.text.SimpleDateFormat
import java.util.*

fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}

//fun AlertDialog.mAlertDialog(title: String, message: String) {
//    AlertDialog.Builder(context).apply {
//        setTitle(title)
//        setMessage(message)
//        setPositiveButton("OK") { di :DialogInterface.OnClickListener ->
//        }
//
//        setNegativeButton("CANCEL") { _, _ -> }
//    }
//
//}


