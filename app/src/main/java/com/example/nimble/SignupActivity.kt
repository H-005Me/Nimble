/**
 * Signup screen
 * Horia Gligor
 */

package com.example.nimble

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

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

            /// get password from tbSignupPassword
            val tbSignupPassword = findViewById<View>(R.id.tbSignupPassword) as EditText
            val password = tbSignupPassword.text.toString()

            /// get password from tbSignupPassword
            val tbSignupConfPassword = findViewById<View>(R.id.tbSignupConfPassword) as EditText
            val confpassword = tbSignupConfPassword.text.toString()

            /// validate signup
            val signupSuccess = validateSignup(email, password, confpassword)
            when (signupSuccess) {
                0 -> { /// success
                    /// go to MainActivity & destroy LoginActivity & SignupActivity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                1 -> { /// email already in use
                    val alert = AlertDialog.Builder(this)
                    alert.setTitle("Sign up Failed")
                    alert.setMessage("E-mail already in use. Try logging in or using a different e-mail")
                    alert.show()
                }
                2 -> { /// password != confpassword
                    val alert = AlertDialog.Builder(this)
                    alert.setTitle("Sign up Failed")
                    alert.setMessage("The password and confirmation password do not match")
                    alert.show()
                }
                else -> { /// should never happen
                    Error(this, 1)
                }
            }
        }
    }
}

/**
 * validateSignup ()
 * returns 0 on success
 * returns 1 if email is already in use
 * returns 2 if password != confpassword
 */
fun validateSignup (email: String, password: String, confpassword: String): Int {
    return 7;
}