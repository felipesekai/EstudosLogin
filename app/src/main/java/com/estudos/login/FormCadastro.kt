package com.estudos.login

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.estudos.login.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class FormCadastro : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormCadastroBinding.inflate(layoutInflater)
    }

    private val mAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private lateinit var userUid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.bntConfirm.setOnClickListener {
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.pass.text.toString()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                userRegister(name, email, password)
            }
        }


    }

    private fun userRegister(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                savedatafromStorage(name, email, password)
//                updateUser(name, email, password)
                Toast.makeText(this, "cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
//                finish()

            } else {
                //trarando erros
                var erro = ""
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    erro = "Digite uma senha com o minimo 6 caracteres"
                } catch (e: FirebaseAuthUserCollisionException) {
                    erro = "Essa conta ja foi cadastrada"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    erro = "Email invalido"
                } catch (e: Exception) {
                    erro = "erro ao cadastrar usuario"
                }

                val snackbar = Snackbar.make(this, binding.root, erro, Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).show()

//               Toast.makeText(this, "erro de autenticacao", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savedatafromStorage(name: String, email: String, password: String) {
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "NOME" to name,
            "EMAIL" to email,
        )

       userUid = mAuth.currentUser?.uid.toString()
        db.collection("Usuario").document(userUid)
                .set(user).addOnSuccessListener {
                    Log.d("db", "sucesso ao salvar os dados")
                }.addOnFailureListener {
                    Log.d("db", "Falha ao salvar os dados $it")
                }
    }

    private fun updateUser(name: String, email: String, password: String) {
        val user = mAuth.currentUser
//        inserindo nome do usuario
        val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(name).build()

        user!!.updateProfile(profileUpdate).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User profile updated.")
            }
        }

    }

    companion object {
        const val TAG = "REGISTER SUCESS"
    }
}