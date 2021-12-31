package com.estudos.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.estudos.login.UserScreen.Companion.USER_EXTRA
import com.estudos.login.databinding.ActivityFormLoginBinding
import com.estudos.login.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class FormLogin : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormLoginBinding.inflate(layoutInflater)
    }
    private var auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(auth.currentUser!=null){
            startProgressBar()
            val user = auth.currentUser
            getProfileFromDatabase(user)


        }else {
            init()
        }
    }

    private fun startProgressBar() {
        binding.apply {
            progressBarContainer.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
        }
    }
    private fun stopProgressBar() {
        binding.apply {
            progressBarContainer.visibility = View.GONE
            progressBar.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        }
    }

    private fun getProfileFromDatabase(user: FirebaseUser?) {
        val db = FirebaseFirestore.getInstance()
        val docUser = db.collection("Usuario").document(user!!.uid)
        docUser.get().addOnSuccessListener {    docSnapshoot ->
            val user = UserModel(
                docSnapshoot.get("NOME") as String,
                docSnapshoot.get("EMAIL") as String
            )
            val intent = Intent(this,UserScreen::class.java)
            intent.putExtra(USER_EXTRA,user)
            startActivity(intent)

        }
    }

    override fun onStop() {
        super.onStop()
        stopProgressBar()
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
                }else{
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun userLogin(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful){
             getProfileFromDatabase(auth.currentUser)
                startProgressBar()
                Toast.makeText(this, "login realizado", Toast.LENGTH_SHORT).show()

            }else {
                Toast.makeText(this, "usuario nao encontrado ou senha invalida", Toast.LENGTH_SHORT).show()

            }

        }
    }

}