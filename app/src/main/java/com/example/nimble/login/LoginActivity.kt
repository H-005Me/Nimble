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
import com.example.nimble.*
import com.example.nimble.database.Database
import com.example.nimble.mainmenu.MainMenu
import com.example.nimble.restaurant_perspective.reservations.MainMenuRestaurantsPerspective
import com.example.nimble.user.user

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /// set btSignup to go to SignupActivity
        val btSignup = findViewById<Button>(R.id.btSignup)
        btSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        // set btRestaurantPerspective to go to MainMenuRestaurantsPerspective
        val btRestaurantPerspective = findViewById<Button>(R.id.btRestaurantPerspective)
        btRestaurantPerspective.setOnClickListener {
            startActivity(Intent(this, MainMenuRestaurantsPerspective::class.java))
        }
        /// set btLoginSubmit to validate login
        val btLoginSubmit = findViewById<Button>(R.id.btLoginSubmit)
        btLoginSubmit.setOnClickListener {
            /// get email from tbLoginEmail
            val tbLoginEmail = findViewById<View>(R.id.tbLoginEmail) as EditText
            val email = tbLoginEmail.text.toString()

            if (email == "") {
                makeSToast(this, "E-mail cannot be empty")
                return@setOnClickListener
            }

            if (!correctEmail(email)) {
                makeSToast(this, "E-mail or password is incorrect")
                return@setOnClickListener
            }

            /// get hash1 for email from db
            val salt = getSalt(email)

            /// get password from tbLoginPassword & hash it
            val tbLoginPassword = findViewById<View>(R.id.tbLoginPassword) as EditText
            val password = hashPassword(tbLoginPassword.text.toString(), salt)

            /// validate login
            if (correctLogin(email, password)) { /// if the account exists and the password is correct
                /// TODO user token and stuff
                /// go to MainActivity & destroy LoginActivity
                val intent = Intent(this, MainMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                //modifies user object,can't change name:
                user.setEmail(email)
                startActivity(intent)
            } else { /// login failed
                makeSToast(this, "E-mail or password is incorrect")
            }
        }
    }

    /**
     * returns true if the email exists
     */
    private fun correctEmail (email: String): Boolean {
        val res = Database.runQuery("""
            SELECT COUNT(*) FROM tbl_users WHERE email = '$email';
        """.trimIndent())
        res!!.next()

        return res.getString(1) == "1"
    }

    /**
     * returns true if the account can be logged into
     */
    private fun correctLogin (email: String, password: String): Boolean {
        /// check if there is a row with the given email and password
        val res = Database.runQuery("""
            SELECT COUNT(*) FROM tbl_users WHERE email = '$email' AND password = '$password';
        """.trimIndent())
        res!!.next()

        return res.getString(1) == "1"
    }

    /**
     * get salt from db (shouldn't be necessary)
     */
    private fun getSalt (email: String): String {
        val res = Database.runQuery("""
            SELECT salt FROM tbl_users WHERE email = '$email';
        """.trimIndent())
        res!!.next()

        return res.getString(1)
    }
}



