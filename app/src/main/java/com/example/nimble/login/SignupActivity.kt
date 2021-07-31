/**
 * Signup screen
 * Horia Gligor
 */

package com.example.nimble.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.nimble.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        /// set btSignupCancel to go to LoginActivity & detroy SignupActivity
        val btSignupCancel = findViewById<Button>(R.id.btSignupCancel)
        btSignupCancel.setOnClickListener {
            finish()
        }

        /// set btSignupSubmit to validate signup
        val btSignupSubmit = findViewById<Button>(R.id.btSignupSubmit)
        btSignupSubmit.setOnClickListener {
            /// get email from tbSignupEmail
            val tbSignupEmail = findViewById<View>(R.id.tbSignupEmail) as EditText
            val email = tbSignupEmail.text.toString()

            if (email == "") {
                alertUser(this, "Sign up Failed", "E-mail cannot be empty")
                return@setOnClickListener
            }

            if (!checkEmail(email)) {
                alertUser(this, "Sign up Failed", "E-mail already in use. Try logging in or using a different e-mail")
                return@setOnClickListener
            }

            /// get time of sign up
            val currentTime = System.currentTimeMillis()

            /// get random number
            val randomNr = (0..1000000000).random() /// 0 - 1 billion

            /// get password from tbSignupPassword & hash it
            val tbSignupPassword = findViewById<View>(R.id.tbSignupPassword) as EditText
            var password = tbSignupPassword.text.toString()

            if (password.length < 6) {
                alertUser(this, "Sign up Failed", "Password must have at least 6 characters")
                return@setOnClickListener
            }

            /// get confpassword from tbSignupConfPassword & hash it
            val tbSignupConfPassword = findViewById<View>(R.id.tbSignupConfPassword) as EditText
            val confpassword = tbSignupConfPassword.text.toString()

            if (password != confpassword) { /// passwords do not match
                alertUser(this, "Sign up Failed", "The password and confirmation password do not match")
                return@setOnClickListener
            }

            /// create hash1
            val hash1 = makeHash1(email, currentTime, randomNr)

            /// hash password
            password = hashPassword(email, password, hash1)

            /// signup success
            /// TODO send verification code (might need a new activity)

            /// TODO if verification code is correct

            /// TODO add user in database
            addUserInDb(email, password, hash1)

            /// go to MainActivity & destroy LoginActivity & SignupActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

            /// TODO else (verification code is incorrect)
            /// TODO Have a "resend code" and a "cancel" button on the alert
            //alertUser(this, "Incorrect code", "The verification code you inputted is incorrect")
        }
    }
}

/**
 * TODO
 * returns true if the email does not exist in the db
 * returns false otherwise
 */
fun checkEmail (email: String): Boolean {
    return true
}

/**
 * TODO
 * adds user in the db
 */
fun addUserInDb (email: String, password: String, hash1: String) {

}