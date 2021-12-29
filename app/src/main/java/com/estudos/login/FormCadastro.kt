package com.estudos.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.estudos.login.databinding.ActivityFormCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class FormCadastro : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormCadastroBinding.inflate(layoutInflater)
    }

    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }

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
                userRegister(name,email,password)
            }
        }


    }

    private fun userRegister(name:String, email:String, password:String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task->
           if (task.isSuccessful){
              updateUser(name,email,password)
               Toast.makeText(this, "cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
               finish()

           }else{
               task.exception

               Toast.makeText(this, "erro de autenticacao", Toast.LENGTH_SHORT).show()
           }
       }
    }

    private fun updateUser(name: String, email: String, password: String) {
        val user = mAuth.currentUser
//        inserindo nome do usuario
        val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(name).build()

        user!!.updateProfile(profileUpdate).addOnCompleteListener {task->
            if (task.isSuccessful){
                Log.d(TAG, "User profile updated.")
            }
        }

    }

    companion object{
        const val TAG = "REGISTER SUCESS"
    }
}