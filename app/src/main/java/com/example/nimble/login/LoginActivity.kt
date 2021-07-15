/**
 * Login screen
 * Horia Gligor
 */

package com.example.nimble.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.nimble.MainActivity
import com.example.nimble.R
import com.example.nimble.alertUser
import com.example.nimble.hashPassword

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /// set btSignup to go to SignupActivity
        val btSignup = findViewById<Button>(R.id.btSignup)
        btSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        /// set btLoginSubmit to validate login
        val btLoginSubmit = findViewById<Button>(R.id.btLoginSubmit)
        btLoginSubmit.setOnClickListener {
            /// get email from tbLoginEmail
            val tbLoginEmail = findViewById<View>(R.id.tbLoginEmail) as EditText
            val email = tbLoginEmail.text.toString()

            /// get password from tbLoginPassword & hash it
            val tbLoginPassword = findViewById<View>(R.id.tbLoginPassword) as EditText
            val password = hashPassword(tbLoginPassword.text.toString())

            /// validate login
            if (validateLogin(email, password)) { /// login success
                /// go to MainActivity & destroy LoginActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else { /// login failed
                alertUser(this, "Login Failed", "E-mail or password is incorrect")
            }
        }
    }
}

fun validateLogin (email: String, password: String): Boolean {
    /// TODO check if email exists
    /// TODO check if password is correct
    return false;
}