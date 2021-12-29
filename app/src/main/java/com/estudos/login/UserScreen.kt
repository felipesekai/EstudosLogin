package com.estudos.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.estudos.login.databinding.ActivityUserScreenBinding
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserScreen : AppCompatActivity() {
    private val binding by lazy {
        ActivityUserScreenBinding.inflate(layoutInflater)
    }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var name: String? = null
    private var emailL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        val user = auth.currentUser

        getProfile(user)


    }

    private fun updateCamps() {
    binding.apply {
        edtUsername.text = name
        edtEmail.text = emailL
    }
    }

    private fun getProfile(user: FirebaseUser?) {
        if (user != null) {
            user.apply {
               name = displayName
                emailL = email

            }
            updateCamps()
        }

    }
}