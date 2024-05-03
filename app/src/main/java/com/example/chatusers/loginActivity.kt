package com.example.chatusers

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {

    lateinit var edtemail: TextInputEditText
    lateinit var edtpassword: TextInputEditText
    lateinit var btnregister: Button
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        edtemail = findViewById(R.id.edtemaillog)
        edtpassword = findViewById(R.id.edtpasswordlog)
        btnregister = findViewById(R.id.btnlog)

        btnregister.setOnClickListener {

            var email = edtemail.text.toString()
            var password = edtpassword.text.toString()
            login(email, password)
            var intent = Intent(this, peopleActivity::class.java)
            startActivity(intent)
        }


    }
    private fun login(email:String, password:String ) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                Toast.makeText(this, "succesful", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "failed to enter credentials", Toast.LENGTH_SHORT).show()
            }
        }

    }

}