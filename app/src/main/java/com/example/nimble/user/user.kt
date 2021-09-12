package com.example.nimble.user

import com.example.nimble.R
import com.example.nimble.database.Database

object user {
    private var username = "Guest"
    private var profilePicture = R.drawable.ic_profile
    private var id = -1
    private var email = "Email@thereIsntAny"
    fun getUser() {
        if (Database.isConnected() && email != "Email@thereIsntAny") {
            val res = Database.runQuery(
                """
                SELECT COUNT(*) FROM tbl_users WHERE email = '$email'
            """.trimIndent()
            )
            res!!.next()
            if (res != null) {

                val userid = res!!.getInt(1)
                this.id = userid
            }
        }
    }

    fun setUsername(new_username: String) {
        this.username = new_username
    }

    fun getUserName(): String {
        return this.username
    }

    fun setProfilePicture(new_profile_picture: Int) {
        this.profilePicture = new_profile_picture
    }

    fun getProfilePicture(): Int {
        return this.profilePicture
    }

    fun getId(): Int {
        return this.id
    }

    fun setEmail(new_email: String) {
        this.email = new_email
        this.getUser()
    }

}