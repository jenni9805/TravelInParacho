package com.example.travelinparacho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtUser=findViewById(R.id.txtUser)
        txtPassword=findViewById(R.id.txtPassword)
        progressBar = findViewById(R.id.progressBar)

        auth= FirebaseAuth.getInstance()

        registrarme.setOnClickListener(){
            val intent= Intent(this,Registrarse::class.java)
            startActivity(intent)
        }
    }

    fun forgotPassword(view:View){

    }

    fun login(view:View){
        loginUser()
    }

    private fun loginUser(){
        val user:String=txtUser.text.toString()
        val password:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        action()
                    }else{
                        Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    private fun action(){
        startActivity(Intent(this,home::class.java))
    }
}
