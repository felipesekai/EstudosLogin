package com.estudos.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.estudos.login.databinding.ActivityFormCadastroBinding
import com.google.firebase.auth.FirebaseAuth

class FormCadastro : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormCadastroBinding.inflate(layoutInflater)
    }

//    private val mAuth by lazy {
//        FirebaseAuth.getInstance()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.bntConfirm.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.pass.text.toString()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }else{
                userRegister(email,password)
            }
        }


    }

    private fun userRegister(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task->
           if (task.isSuccessful){
               Toast.makeText(this, "cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
               finish()
//               val user = mAuth.currentUser
//               updateUI(user)
           }else{
               Toast.makeText(this, "erro de autenticacao", Toast.LENGTH_SHORT).show()
           }
       }
    }
}