package com.example.chatusers

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    lateinit var recycler:RecyclerView
    lateinit var editText: EditText
    lateinit var imageView: ImageView
    lateinit var messageAdapter: MessageAdapter
    lateinit var messagelist:ArrayList<message>
    lateinit var database:FirebaseDatabase

    var recieverooom:String? = null
    var sendroom :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var name = intent.getStringExtra("name")
        var recieveruid = intent.getStringExtra("uid")

        var senderuid = FirebaseAuth.getInstance().currentUser?.uid

        sendroom = recieveruid + senderuid
        recieverooom = senderuid + recieveruid

        supportActionBar?.title = name

        recycler = findViewById(R.id.chatrecy)
        editText = findViewById(R.id.edttext)
        imageView = findViewById(R.id.imgsend)


        messagelist = ArrayList()
        messageAdapter = MessageAdapter(messagelist)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = messageAdapter

        database = FirebaseDatabase.getInstance()

        //add data to recycler view

        database.getReference().child("chats").child(sendroom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                    for(data in snapshot.children){

                        var value = data.getValue(message::class.java)
                        messagelist.add(value!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        imageView.setOnClickListener {

            val message = editText.text.toString()
            val messageObject = message(message,senderuid)
            database.getReference().child("charts").child(sendroom!!).child("messages").setValue(messageObject)
                .addOnSuccessListener {
                    database.getReference().child("charts").child(recieverooom!!).child("messages").setValue(messageObject)

                }
            editText.setText("")
        }



    }
}