package com.estudos.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.estudos.login.databinding.ActivityUserScreenBinding
import com.estudos.login.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

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

        val userModel: UserModel? = intent.getParcelableExtra(USER_EXTRA)

        getProfile(user)
        binding.edtUsername.text = userModel?.name
        onClick()


    }

    private fun onClick() {
        binding.btnSignout.setOnClickListener {
            auth.signOut()
            finish()
        }
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

    companion object {
        const val USER_EXTRA = "user_model"
    }
}