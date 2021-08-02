package com.example.nimble

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

/**
 * Usually you call it like this: errorMsg(this, n) where n is the error code (also write it in errcodes.txt)
 */
fun errorMsg (activity: AppCompatActivity, errcode: Int, errmsg: String) {
    val alert = AlertDialog.Builder(activity)
    alert.setTitle("Error")
    alert.setMessage("An error has occurred.\nError code: $errcode\nError message: $errmsg")
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
 * returns the hashed password
 */
fun hashPassword (email: String, password: String, salt: String): String {
    return sha256(email + password + salt)
}

/**
 * returns salt (which is used in hashing the password & is stored in the db)
 */
fun makeSalt (email: String, time: Long, randomNr: Int): String {
    return sha256(time.toString() + email + randomNr.toString())
}