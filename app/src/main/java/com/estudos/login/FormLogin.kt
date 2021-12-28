package com.estudos.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.estudos.login.databinding.ActivityFormLoginBinding
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormLoginBinding.inflate(layoutInflater)
    }
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()

    }

    private fun init() {
       binding.txtCad.setOnClickListener {
           startActivity(Intent(this, FormCadastro::class.java))
       }
        binding.btnLogin.setOnClickListener {
            val email = binding.edtUsername.text.toString()
            val password = binding.edtPass.text.toString()
                if (email.isNotEmpty() || password.isNotEmpty()) {
                        userLogin(email, password)
                }
        }
    }

    private fun userLogin(email: String, password: String) {
       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful){
                val user = auth.currentUser
//                user.email
//                user.displayName
                Toast.makeText(this, "login realizado", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this, "usuario nao encontrado ou senha invalida", Toast.LENGTH_SHORT).show()

            }

        }
    }
}