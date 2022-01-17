package com.example.nimble

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2Mode
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
 * Usually you call it like this: alertUser(this, title, message) where title is the title of the alert and message is the message of the alert
 */
fun alertUser (activity: AppCompatActivity, title: String, msg: String) {
    val alert = AlertDialog.Builder(activity)
    alert.setTitle(title)
    alert.setMessage(msg)
    alert.show()
}

/**
 * Usually you call it like this: makeToast(this, message, Toast.LENGTH_SHORT)
 */
fun makeToast (activity: AppCompatActivity, msg: String, length: Int) {
    Toast.makeText(activity, msg, length).show()
}

/**
 * make short toast
 */
fun makeSToast (activity: AppCompatActivity, msg: String) {
    makeToast(activity, msg, Toast.LENGTH_SHORT)
}

/**
 * make long toast
 */
fun makeLToast (activity: AppCompatActivity, msg: String) {
    makeToast(activity, msg, Toast.LENGTH_LONG)
}

/** !!! NOTE: do not use sha, use argon2 */

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
    //return sha256(email + password + salt)

    val mode = Argon2Mode.ARGON2_I

    val argon2 = Argon2Kt(); /// init argon2

    val hash = argon2.hash( /// hash result as string
        mode = mode,
        password = password.toByteArray(),
        salt = salt.toByteArray(),
    ).encodedOutputAsString()

    val res = hash.substring(hash.lastIndexOf('$') + 1) /// only what is after the last '$' in res is the actual hash (128 bytes I think)

    Log.d("dbg", "${res.length}")
    Log.d("dbg", res)

    return res
}

/**
 * returns salt (which is used in hashing the password & is stored in the db)
 */
fun makeSalt (email: String, time: Long, randomNr: Int): String {
    return sha256(time.toString() + email + randomNr.toString())
}