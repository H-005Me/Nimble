package com.example.nimble

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

fun Error (activity: AppCompatActivity, errcode: Int) {
    val alert = AlertDialog.Builder(activity)
    alert.setTitle("Error")
    alert.setMessage("An error has occurred. Please contact the developers.\nError code: $errcode")
    alert.show()
}