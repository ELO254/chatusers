package com.example.chatusers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var edtemail:TextInputEditText
    lateinit var edtpassword:TextInputEditText
    lateinit var btnregister: Button
    lateinit var auth:FirebaseAuth
    lateinit var data:FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        edtemail = findViewById(R.id.edtemail)
        edtpassword = findViewById(R.id.edtpassword)
        btnregister = findViewById(R.id.btnreg)
        var textlog:TextView = findViewById(R.id.txtlogin)

        btnregister.setOnClickListener {

            var email = edtemail.text.toString()
            var password = edtpassword.text.toString()
            register(email, password)
        }

        textlog.setOnClickListener{
            var intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun register(email:String, password:String ) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                addUser(email,auth.currentUser?.uid!!)
                Toast.makeText(this, "succesful", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, loginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "failed to enter credentials", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun addUser(email: String, uid: String) {
        data = FirebaseDatabase.getInstance()
        data.getReference().child("user").child(uid).setValue(email)


    }
}