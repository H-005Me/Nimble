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
import com.example.nimble.mainmenu.MainMenu
import com.example.nimble.*
import com.example.nimble.database.Database
import com.example.nimble.user.user

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        /// set btSignupCancel to go to LoginActivity & destroy SignupActivity
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
                makeSToast(this, "E-mail cannot be empty")
                return@setOnClickListener
            }

            if (!checkEmail(email)) {
                makeSToast(this, "E-mail already in use. Try logging in or using a different e-mail")
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
                makeSToast(this, "Password must have at least 6 characters")
                return@setOnClickListener
            }

            /// get confpassword from tbSignupConfPassword & hash it
            val tbSignupConfPassword = findViewById<View>(R.id.tbSignupConfPassword) as EditText
            val confpassword = tbSignupConfPassword.text.toString()

            if (password != confpassword) { /// passwords do not match
                makeSToast(this, "The password and confirmation password do not match")
                return@setOnClickListener
            }

            /// get first name
            val tbFirstName = findViewById<View>(R.id.tbFirstName) as EditText
            val firstName = tbFirstName.text.toString()

            if (firstName.isEmpty()) {
                makeSToast(this, "First name cannot be empty")
                return@setOnClickListener
            }
            if (firstName.length > 64) {
                makeSToast(this, "First name cannot have more than 64 characters")
            }

            /// get last name
            val tbLastName = findViewById<View>(R.id.tbLastName) as EditText
            val lastName = tbLastName.text.toString()

            if (lastName.isEmpty()) {
                makeSToast(this, "First name cannot be empty")
                return@setOnClickListener
            }
            if (lastName.length > 64) {
                makeSToast(this, "Last name cannot have more than 64 characters")
            }

            /// get phone number
            val tbPhone = findViewById<View>(R.id.tbPhone) as EditText
            val phone = tbPhone.text.toString()

            if (phone.isEmpty()) {
                makeSToast(this, "The phone number cannot be empty")
                return@setOnClickListener
            }
            if (phone.length > 32) {
                makeSToast(this, "The phone number cannot have more than 32 characters")
                return@setOnClickListener
            }

            /// create salt
            val salt = makeSalt(email, currentTime, randomNr)

            /// hash password
            password = hashPassword(email, password, salt)

            /// signup success
            /// TODO send verification code (might need a new activity)

            /// TODO if verification code is correct

            /// add user in database
            addUserInDb(email, password, salt, firstName, lastName, phone)

            /// go to MainActivity & destroy LoginActivity & SignupActivity
            val intent = Intent(this, MainMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            //changes the user objects
            user.setEmail(email)
            user.setUsername(lastName)
            //
            startActivity(intent)

            /// TODO else (verification code is incorrect)
            /// TODO Have a "resend code" and a "cancel" button on the alert
            //alertUser(this, "Incorrect code", "The verification code you inputted is incorrect")
        }
    }

    /**
     * returns true if the email does not exist in the db
     * returns false otherwise
     */
    private fun checkEmail (email: String): Boolean {
        /// get number of rows where email is correct
        val res = Database.runQuery("""
            SELECT COUNT(*) FROM tbl_users WHERE email = '$email'; 
        """.trimIndent())

        res!!.next()
        return res.getString(1) == "0"
    }

    private fun addUserInDb (email: String, password: String, salt: String, firstName: String, lastName: String, phone: String) {
        Database.runUpdate("""
            INSERT INTO tbl_users (email, salt, password, firstName, lastName, phone) VALUES ('$email', '$salt', '$password', '$firstName', '$lastName', '$phone');
        """.trimIndent())
    }
}
