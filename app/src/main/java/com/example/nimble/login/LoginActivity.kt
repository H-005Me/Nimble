/**
 * Login screen
 * Horia Gligor
 */

package com.example.nimble.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.nimble.*
import com.example.nimble.database.Database

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /// set btSignup to go to SignupActivity
        val btSignup = findViewById<Button>(R.id.btSignup)
        btSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        /// set btLoginSubmit to validate login
        val btLoginSubmit = findViewById<Button>(R.id.btLoginSubmit)
        btLoginSubmit.setOnClickListener {
            /// get email from tbLoginEmail
            val tbLoginEmail = findViewById<View>(R.id.tbLoginEmail) as EditText
            val email = tbLoginEmail.text.toString()

            /// get hash1 for email from db
            val hash1 = getHash1(email)

            /// get password from tbLoginPassword & hash it
            val tbLoginPassword = findViewById<View>(R.id.tbLoginPassword) as EditText
            val password = hashPassword(email, tbLoginPassword.text.toString(), hash1)

            /// validate login
            val (loginSuccess, userToken) = validateLogin(email, password)
            if (loginSuccess) { /// login succeeded
                /// TODO
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

/**
 * returns (loginSuccess, userToken) - if loginSuccess == false, userToken is ""
 */
fun validateLogin (email: String, password: String): Pair<Boolean, String> {
    /// TODO check if email exists
    /// TODO check if password is correct

    /// TODO if both exist, get userToken associated with that email & return (true, userId)
    return Pair(false, "")
}

/**
 * TODO
 * get hash1 from db
 */
fun getHash1 (email: String): String {
    val hash = ""
    return hash
}