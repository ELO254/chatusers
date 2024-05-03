package com.example.chatusers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class peopleActivity : AppCompatActivity() {

    lateinit var database:FirebaseDatabase
    lateinit var RecyclerView:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_people)

        var users = mutableListOf<Any>()


        RecyclerView = findViewById(R.id.recycler)
        var adapter = userrAdapter(this,users)

        RecyclerView.adapter = adapter
        RecyclerView.layoutManager = LinearLayoutManager(this)



        database = FirebaseDatabase.getInstance()

        database.getReference().child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                users.clear()
                for(data in snapshot.children){
                    var value = data.getValue(String::class.java)
                    if (value != null) {
                        users.add(value)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("error","${error}")
            }

        })
    }
}