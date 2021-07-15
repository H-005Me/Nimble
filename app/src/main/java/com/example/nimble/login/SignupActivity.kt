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
import androidx.appcompat.app.AlertDialog
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

            /// get password from tbSignupPassword & hash it
            val tbSignupPassword = findViewById<View>(R.id.tbSignupPassword) as EditText
            val password = hashPassword(tbSignupPassword.text.toString())

            /// get password from tbSignupPassword & hash it
            val tbSignupConfPassword = findViewById<View>(R.id.tbSignupConfPassword) as EditText
            val confpassword = hashPassword(tbSignupConfPassword.text.toString())

            /// validate signup
            val signupSuccess = validateSignup(email, password, confpassword)
            when (signupSuccess) {
                0 -> { /// success
                    /// TODO send verification code (might need a new activity)

                    /// TODO if verification code is correct
                    /// TODO add email & password in database
                    /// go to MainActivity & destroy LoginActivity & SignupActivity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    /// TODO else (verification code is incorrect)
                    /// TODO Have a "resend code" and a "cancel" button on the alert
                    //alertUser(this, "Incorrect code", "The verification code you inputted is incorrect")
                }
                1 -> { /// email already in use
                    alertUser(this, "Sign up Failed", "E-mail already in use. Try logging in or using a different e-mail")
                }
                2 -> { /// password != confpassword
                    alertUser(this, "Sign up Failed", "The password and confirmation password do not match")
                }
                else -> { /// should never happen
                    errorMsg(this, 1)
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
    /// TODO Check email
    /// Check password != confpassword
    if (password != confpassword)
        return 2
    return 0
}