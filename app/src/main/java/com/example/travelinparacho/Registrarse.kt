package com.example.travelinparacho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registrarse.*
import java.lang.ref.Reference

class Registrarse : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPass: EditText
    private lateinit var txtLastname: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)
        txtName=findViewById(R.id.txtName)
        txtEmail=findViewById(R.id.txtEmail)
        txtPass=findViewById(R.id.txtPass)
        txtLastname=findViewById(R.id.txtLastname)


        progressBar = findViewById(R.id.progressBar)

        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()
        dbReference=database.reference.child("User")


        fun verifyEmail(user:FirebaseUser?){
            user?.sendEmailVerification()
                ?.addOnCompleteListener(this){
                        task ->
                    if (task.isComplete){
                        Toast.makeText(this, "Email enviado", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_LONG).show()
                    }
                }
        }

        fun action(){
            startActivity(Intent(this, MainActivity::class.java))
        }

         fun createNewAccount(){
            val name:String=txtName.text.toString()
            val email:String=txtEmail.text.toString()
            val pass:String=txtPass.text.toString()
            val lastName:String=txtLastname.text.toString()

            if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(lastName) ){
                progressBar.visibility=View.VISIBLE
                
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) {
                        task ->
                        if (task.isComplete){
                            val user:FirebaseUser?=auth.currentUser
                            verifyEmail(user)

                            val userBD=dbReference.child(user?.uid.toString())

                            userBD.child("Name").setValue(name)
                            userBD.child("LastName").setValue(lastName)
                            action()
                        }
                    }
            }
        }

        fun register(view:View) {
            createNewAccount()
        }

        cuenta.setOnClickListener(){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
