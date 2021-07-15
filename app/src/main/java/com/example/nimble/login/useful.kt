package com.example.nimble

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

/**
 * Usually you call it like this: errorMsg(this, n) where n is the error code (also write it in errcodes.txt)
 */
fun errorMsg (activity: AppCompatActivity, errcode: Int) {
    val alert = AlertDialog.Builder(activity)
    alert.setTitle("Error")
    alert.setMessage("An error has occurred. Please contact the developers.\nError code: $errcode")
    alert.show()
}

/**
 * Usually you call it like this: alertUser(this, title, message) where title is the title of the alert and message is the message of the allert
 */
fun alertUser (activity: AppCompatActivity, title: String, msg: String) {
    val alert = AlertDialog.Builder(activity)
    alert.setTitle(title)
    alert.setMessage(msg)
    alert.show()
}

/**
 * sha256(my_string) returns the sha256 hash of my_string
 */
fun sha256 (str: String): String {
    return MessageDigest.getInstance("SHA-256").digest(str.toByteArray()).fold("", {str, it -> str + "%02x".format(it)})
}

/**
 * hashPassword(my_password) returns the hashed password
 * TODO should also add salt here
 */
fun hashPassword (password: String): String {
    return sha256(password)
}